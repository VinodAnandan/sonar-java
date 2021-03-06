/*
 * SonarQube Java
 * Copyright (C) 2012-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AssertionsInTestsCheckTest {

  private AssertionsInTestsCheck check = new AssertionsInTestsCheck();

  @Parameters
  public static Collection<Object[]> frameworks() {
    return Arrays.asList(new Object[][] {
      {"Junit4"},
      {"AssertJ"},
      {"Hamcrest"},
      {"Spring"},
      {"EasyMock"},
      {"Truth"},
      {"RestAssured"},
      {"Mockito"},
      {"JMock"},
      {"WireMock"}
    });
  }

  public String framework;

  public AssertionsInTestsCheckTest(String framework) {
    this.framework = framework;
  }

  @Test
  public void test() {
    JavaCheckVerifier.verify("src/test/files/checks/AssertionsInTestsCheck/" + framework + ".java", check);
  }
}
