import java.util.List;
import java.util.Random;
import java.util.stream.*;

class A {

  void test() {
    IntStream.range(0, 10).filter(i -> true).findFirst().isPresent(); // Noncompliant [[sc=28;ec=67]] {{Replace this "filter().findFirst().isPresent()" chain with "anyMatch()"}}
    IntStream.range(0, 10).filter(i -> true).findAny().isPresent(); // Noncompliant [[sc=28;ec=65]] {{Replace this "filter().findAny().isPresent()" chain with "anyMatch()"}}
    LongStream.range(0, 10).filter(i -> true).findFirst().isPresent(); // Noncompliant
    LongStream.range(0, 10).filter(i -> true).findAny().isPresent(); // Noncompliant
    Random.doubles(10).filter(i -> true).findFirst().isPresent(); // Noncompliant
    Random.doubles(10).filter(i -> true).findAny().isPresent(); // Noncompliant
    Stream.of("a", "b", "c").filter(s -> s.startsWith("a")).findFirst().isPresent(); // Noncompliant
    Stream.of("a", "b", "c").filter(s -> s.startsWith("a")).findAny().isPresent(); // Noncompliant

    Stream.of("a", "b", "c").findAny().isPresent(); // Compliant
  }

}
