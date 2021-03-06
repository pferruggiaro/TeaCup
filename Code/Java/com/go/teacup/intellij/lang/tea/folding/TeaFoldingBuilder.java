package com.go.teacup.intellij.lang.tea.folding;

import com.go.teacup.intellij.lang.tea.lexer.TeaTokenTypes;
import com.go.teacup.intellij.lang.tea.parser.TeaElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * User: JACKSBRR
 * Created: Apr 3, 2007 11:07:30 AM
 */
public class TeaFoldingBuilder implements FoldingBuilder {
    public FoldingDescriptor[] buildFoldRegions(ASTNode node, Document document) {
      List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
      appendDescriptors(node, document, descriptors);
      return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    private void appendDescriptors(final ASTNode node, final Document document, final List<FoldingDescriptor> descriptors) {
      if (node.getElementType() == TeaElementTypes.BLOCK) {
        if (document.getLineNumber(node.getStartOffset()) != document.getLineNumber(node.getTextRange().getEndOffset())) {
          descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
        }
      }
//      else if (node.getElementType() == TeaTokenTypes.DOC_COMMENT) {
//        descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
//      }
      else if (node.getElementType() == TeaTokenTypes.C_STYLE_COMMENT) {
        descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
      }
//      else if (node.getElementType() == TeaElementTypes.CODE_REGION) {
//        descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
//      }

      ASTNode child = node.getFirstChildNode();
      while (child != null) {
        appendDescriptors(child, document, descriptors);
        child = child.getTreeNext();
      }
    }

    public String getPlaceholderText(ASTNode node) {
//      if (node.getElementType() == TeaTokenTypes.DOC_COMMENT) {
//        return "/**...*/";
//      } else
      if (node.getElementType() == TeaTokenTypes.C_STYLE_COMMENT) {
        return "/*...*/";
      }
      else if (node.getElementType() == TeaElementTypes.BLOCK) {
        return "{...}";
      }
//      else if (node.getElementType() == TeaElementTypes.CODE_REGION) {
//        return "<%...%>";
//      }
      return null;
    }

    public boolean isCollapsedByDefault(ASTNode node) {
      return false; //CodeFoldingSettings.getInstance().isCollapseJavadocs() && node.getElementType() == TeaTokenTypes.DOC_COMMENT;
    }
}
