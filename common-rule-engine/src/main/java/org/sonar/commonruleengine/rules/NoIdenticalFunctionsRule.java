package org.sonar.commonruleengine.rules;

import org.sonar.uast.UastNode;

/**
 * Rule https://jira.sonarsource.com/browse/RSPEC-4144
 */
public class NoIdenticalFunctionsRule extends CommonRule {

  @Override
  public void visitNode(UastNode node) {
    if (node.kinds.contains(UastNode.Kind.FUNCTION)) {
      // dummy implementation which reports every method
      reportIssue(node, "Issue here");
    }
  }
}
