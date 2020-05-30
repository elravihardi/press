package me.saket.press.shared.sync

import io.ktor.client.HttpClient
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.UnstableDefault
import me.saket.kgit.GitRepository
import me.saket.kgit.RealGit
import me.saket.press.shared.di.koin
import me.saket.press.shared.sync.git.AppStorage
import me.saket.press.shared.sync.git.GitHost
import me.saket.press.shared.sync.git.GitHub
import me.saket.press.shared.sync.git.GitSyncer
import org.koin.dsl.module

class SharedSyncComponent {

  val module = module {
    single { httpClient() }
    factory { SyncPreferencesPresenter(get()) }
    factory<GitHost> { GitHub(get()) }

    factory { RealGit().repository(get<AppStorage>().path) }
    factory<Syncer> { GitSyncer(get(), get()) }
  }

  @OptIn(UnstableDefault::class)
  private fun httpClient(): HttpClient {
    return HttpClient {
//      install(JsonFeature) {
//        serializer = KotlinxSerializer(Json(JsonConfiguration.Stable.copy(
//            prettyPrint = true,
//            ignoreUnknownKeys = true
//        )))
//      }
      install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
      }
    }
  }

  companion object {
    fun syncer(): GitHost = koin()
    fun presenter(): SyncPreferencesPresenter = koin()
  }
}