/*
 * SonarQube Java
 * Copyright (C) 2012-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.List;

@Rule(
  key = "S2257",
  name = "Only standard cryptographic algorithms should be used",
  priority = Priority.BLOCKER,
  tags = {Tag.CWE, Tag.OWASP_A6, Tag.SANS_TOP_25_POROUS, Tag.SECURITY})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SECURITY_FEATURES)
@SqaleConstantRemediation("1d")
public class CustomCryptographicAlgorithmCheck extends SubscriptionBaseVisitor {

  private static final String MESSAGE_DIGEST_QUALIFIED_NAME = "java.security.MessageDigest";

  @Override
  public List<Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.CLASS);
  }

  @Override
  public void visitNode(Tree tree) {
    ClassTree classTree = (ClassTree) tree;
    if (hasSemantic() && isJavaSecurityMessageDigestSubClass(classTree)) {
      reportIssue(ExpressionsHelper.reportOnClassTree(classTree), "Use a standard algorithm instead of creating a custom one.");
    }
  }

  private static boolean isJavaSecurityMessageDigestSubClass(ClassTree tree) {
    Symbol.TypeSymbol classSymbol = tree.symbol();
    // Corner case: A type is a subtype of itself
    return classSymbol != null && !classSymbol.type().is(MESSAGE_DIGEST_QUALIFIED_NAME) &&
      classSymbol.type().isSubtypeOf(MESSAGE_DIGEST_QUALIFIED_NAME);
  }
}
