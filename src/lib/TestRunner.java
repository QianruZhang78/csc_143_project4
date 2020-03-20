package lib;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Request;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static class TestResults {
        public String className = "";
        public int testCount = 0;
        public int successCount = 0;
        public int failureCount = 0;
        public ArrayList<String> failures;

        private TestResults(String className, int testCount, int failureCount, List<Failure> failures) {
            this.className = className;
            this.testCount = testCount;
            this.successCount = testCount - failureCount;
            this.failureCount = failureCount;

            if (this.failureCount == 0) {
                return;
            }

            this.failures = new ArrayList<String>();
            for (Failure failedTest : failures) {
                this.failures.add(failedTest.getTestHeader());
            }
        }

        public boolean allPassed() {
            return successCount == testCount;
        }

        public String toString() {
            String base = String.format("%s [%d/%d]\n", this.className, this.successCount, this.testCount);
            if (failureCount > 0) {
                base += "The following test(s) failed:\n";
                for (String f : this.failures) {
                    base += String.format(" - %s\n", f);
                }
            }
            return base;
        }
    }

    public static TestResults runTest(Class tests) {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(tests);
        return new TestResults(tests.getName(),
                result.getRunCount(), result.getFailureCount(), result.getFailures());
    }
}
