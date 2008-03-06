package com.go.teacup.intellij.lang.tea.psi.impl;

import com.go.teacup.intellij.lang.tea.psi.*;
import com.go.teacup.intellij.lang.tea.validation.TeaElementVisitor;
import com.go.teacup.intellij.lang.tea.TeaElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;

/**
 * User: jacksbrr
 * Created: Apr 16, 2007 3:25:22 PM
 */
public class TeaAssignmentStatementImpl extends TeaStatementImpl implements TeaAssignmentStatement {
    public TeaAssignmentStatementImpl(final ASTNode node) {
      super(node);
    }

    public void accept(PsiElementVisitor visitor) {
      if (visitor instanceof TeaElementVisitor) {
        ((TeaElementVisitor)visitor).visitTeaAssignmentStatement(this);
      }
      else {
        visitor.visitElement(this);
      }
    }

    public TeaType getType() {
        final ASTNode childNode = getNode().findChildByType(TeaElementTypes.TYPE);
        return childNode == null ? null : (TeaType)childNode.getPsi();
    }

    public TeaVariable getVariable() {
        final ASTNode childNode = getNode().findChildByType(TeaElementTypes.VARIABLE);
        return childNode == null ? null : (TeaVariable)childNode.getPsi();
    }

    public boolean processDeclarations(PsiScopeProcessor processor, PsiSubstitutor substitutor, PsiElement lastParent, PsiElement place) {
//        if (lastParent != null) {
          final TeaVariable var = getVariable();
          if (var != null) return processor.execute(var, substitutor);
//          else {
//            if (!processor.execute(getVariableExpression(), null)) return false;
//          }
//        }
        return true;
    }

}