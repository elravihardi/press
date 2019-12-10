package me.saket.wysiwyg.parser.highlighters

import me.saket.wysiwyg.parser.SpanWriter
import me.saket.wysiwyg.parser.node.Strikethrough
import me.saket.wysiwyg.parser.node.endOffset
import me.saket.wysiwyg.parser.node.startOffset

class StrikethroughVisitor : DelimitedNodeVisitor<Strikethrough>() {

  override fun visit(
    node: Strikethrough,
    writer: SpanWriter
  ) {
    writer.addStrikethrough(
        from = node.startOffset,
        to = node.endOffset
    )
    writer.addForegroundColor(
        color = writer.style.strikethroughTextColor,
        from = node.startOffset,
        to = node.endOffset
    )
    super.visit(node, writer)
  }
}
