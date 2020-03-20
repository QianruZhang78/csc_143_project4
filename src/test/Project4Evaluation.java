package test;

import lib.TestRunner;

public class Project4Evaluation {
    public static void main(String[] args) {
        int totalTests = 25;

        System.out.println("BEGIN PROJECT 4 EVALUATION");
        System.out.println("Total base tests: " + totalTests);
        System.out.println("==========================");
        System.out.println();

        int testsRun = 0;
        int testsPassed = 0;
        boolean allPassed = true;

        TestRunner.TestResults sortingResults = TestRunner.runTest(SortingTest.class);
        System.out.println(sortingResults.toString());
        allPassed = allPassed && sortingResults.allPassed();

        testsRun += sortingResults.testCount;
        testsPassed += sortingResults.successCount;

        TestRunner.TestResults heapResults = TestRunner.runTest(HeapTest.class);
        System.out.println(heapResults.toString());
        allPassed = allPassed && heapResults.allPassed();

        testsRun += heapResults.testCount;
        testsPassed += heapResults.successCount;

        TestRunner.TestResults hashMapResults = TestRunner.runTest(HashMapTest.class);
        System.out.println(hashMapResults.toString());
        allPassed = allPassed && hashMapResults.allPassed();

        testsRun += hashMapResults.testCount;
        testsPassed += hashMapResults.successCount;

        if (testsRun < totalTests) {
            System.out.printf("Note: %d tests were excluded from evaluation.\n", totalTests - testsRun);
        }

        if (testsRun == totalTests && allPassed) {
            System.out.println("!!! ALL BASE TESTS PASSED! GREAT JOB !!!");
        }

        System.out.printf("Base Tests Passed: [%d / %d]\n", testsPassed, testsRun);
        long baseProjectScore = Math.round(Math.floor(((double)(testsPassed) / (double)(totalTests)) * 100));
        System.out.printf("Base Project Score: %d%%\n", baseProjectScore);

        // Evaluate bonus section

        int bonusTests = 4;
        int bonusTestsRun = 0;
        int bonusTestsPassed = 0;
        boolean bonusPassed = allPassed;

        System.out.println();
        System.out.println();
        System.out.println("BEGIN P4 BONUS EVALUATION");
        System.out.println("Total bonus tests: " + bonusTests);
        System.out.println("=========================");

        TestRunner.TestResults priorityQueueResults = TestRunner.runTest(PriorityQueueTest.class);
        System.out.println(priorityQueueResults.toString());
        bonusPassed = bonusPassed && priorityQueueResults.allPassed();

        bonusTestsRun += priorityQueueResults.testCount;
        bonusTestsPassed += priorityQueueResults.successCount;

        if (bonusTestsRun < bonusTests) {
            System.out.printf("Note: %d bonus tests were excluded from evaluation.\n", totalTests - testsRun);
        }

        if (testsRun == totalTests && bonusTests == bonusTestsPassed && bonusPassed) {
            System.out.println("!!! ALL PROJECT AND BONUS TESTS PASSED! GREAT JOB !!!");
            System.out.println("!!! YOU MADE IT, ALL CSC 143 PROJECTS COMPLETE !!!");
        }

        System.out.printf("Bonus Tests Passed: [%d / %d]\n", bonusTestsPassed, bonusTestsRun);
        long bonusTestScore = Math.round(Math.floor(((double)(bonusTestsPassed) / (double)(bonusTests))* 10));

        System.out.printf("TOTAL Project Score: %d%%\n",
                baseProjectScore + bonusTestScore);
    }
}
