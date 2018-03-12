package org.sonar.commonruleengine.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.sonar.commonruleengine.Engine;
import org.sonar.commonruleengine.Issue;
import org.sonar.commonruleengine.UastUtils;
import org.sonar.uast.UastNode;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

  static List<Integer> expectedLines(InputStream source) throws IOException {
    List<Integer> expectedIssues = new ArrayList<>();
    int lineCounter = 1;
    BufferedReader br = new BufferedReader(new InputStreamReader(source));
    String line;
    while ((line = br.readLine()) != null) {
      if (line.contains("Noncompliant")) {
        expectedIssues.add(lineCounter);
      }
      lineCounter++;
    }
    return expectedIssues;
  }

  public static void checkRuleOnJava(Check check) throws IOException {
    String checkName = check.getClass().getSimpleName();
    String sourceFilename = "java/" + checkName + "/" + checkName + ".java";
    checkRule(check, sourceFilename);
  }

  public static void checkRuleOnJava(Check check, String filename) throws IOException {
    String sourceFilename = "java/" + check.getClass().getSimpleName() + "/" + filename;
    checkRule(check, sourceFilename);
  }

  public static void checkRuleOnGo(Check check) throws IOException {
    checkRuleOnGo(check, check.getClass().getSimpleName() + ".go");
  }

  public static void checkRuleOnGo(Check check, String filename) throws IOException {
    String sourceFilename = "go/" + check.getClass().getSimpleName() + "/" + filename;
    checkRule(check, sourceFilename);
  }

  public static void checkRule(Check check, String filename) throws IOException {
    String fullFilename = "src/test/files/checks/" + filename;
    UastNode uast = UastUtils.fromFile(new File(fullFilename + ".uast.json"));
    Engine engine = new Engine(Collections.singletonList(check));
    List<Issue> issues = engine.scan(uast).issues;
    List<Integer> actualLines = issues.stream().map(Issue::getLine).collect(Collectors.toList());
    List<Integer> expectedLines = expectedLines(new FileInputStream(new File(fullFilename)));
    assertThat(actualLines).containsExactlyInAnyOrderElementsOf(expectedLines);
  }
}

