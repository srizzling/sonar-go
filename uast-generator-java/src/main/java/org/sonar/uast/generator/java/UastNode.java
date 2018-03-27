package org.sonar.uast.generator.java;

import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * This class copies structure of {@link org.sonar.uast.UastNode}, but we don't want dependency on common-rule-engine project
 */
class UastNode {
  Set<Kind> kinds;
  String nativeNode;
  Token token;
  List<UastNode> children;

  public UastNode(Set<Kind> kinds, String nativeNode, @Nullable Token token, List<UastNode> children) {
    this.kinds = kinds;
    this.nativeNode = nativeNode;
    this.token = token;
    this.children = children;
  }

  enum Kind {
    ASSIGNMENT,
    ASSIGNMENT_OPERATOR,
    ASSIGNMENT_TARGET,
    ASSIGNMENT_VALUE,
    BLOCK,
    BINARY_EXPRESSION,
    EXPRESSION,
    PARENTHESIZED_EXPRESSION,
    CASE,
    CLASS,
    COMPILATION_UNIT,
    CONDITION,
    DEFAULT_CASE,
    ELSE,
    EOF,
    KEYWORD,
    LOOP,
    FUNCTION,
    FUNCTION_NAME,
    FUNCTION_LITERAL,
    IDENTIFIER,
    IF,
    LITERAL,
    BOOLEAN_LITERAL,
    PARAMETER,
    STATEMENT,
    COMMENT,
    OPERATOR,
    OPERATOR_ADD,
    OPERATOR_EQUAL,
    OPERATOR_LOGICAL_AND,
    OPERATOR_LOGICAL_OR,
    OPERATOR_MULTIPLY,
    OPERATOR_NOT_EQUAL,
    SWITCH,
  }

  static class Token {
    String value;
    int line;
    int column;

    public Token(int line, int column, String value) {
      this.value = value;
      this.line = line;
      this.column = column;
    }
  }
}

