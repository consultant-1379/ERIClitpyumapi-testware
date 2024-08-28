package com.ericsson.nms.litp.taf.test.cases;

import java.util.List;
import java.util.Map;
import java.io.*;

import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.*;
import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.cifwk.taf.tools.cli.TimeoutException;

import com.ericsson.nms.litp.taf.operators.RPMUpgrade;
import com.ericsson.nms.litp.taf.operators.PythonTestRunner;
import com.ericsson.nms.litp.taf.operators.Error;

import javax.inject.Inject;


public class LITPyumapiTestRunner extends TorTestCaseHelper {
    
    Logger logger = Logger.getLogger(LITPyumapiTestRunner.class);

    @Inject
    private RPMUpgrade rpmUpgradeOperator;
    
    @Inject
    private PythonTestRunner pythonTestRunnerOperator;

    /**
     * @throws TimeoutException,FileNotFoundException
     * @DESCRIPTION Upgrade specific LITP rpm
     * @PRE Connection to SUT
     * @PRIORITY HIGH
     */

    @TestId(id = "CXP9031124-1", title = "Upgrade ERIClitpyumapi rpm on the MS")
    @Test(groups={"ACCEPTANCE"})
    public void upgradeERIClitpyumapiRPM() throws TimeoutException, FileNotFoundException {

        rpmUpgradeOperator.initialise("yumapi");

        assertEquals(0, rpmUpgradeOperator.upgradeRPM());
    }

    /**
     * @throws TimeoutException
     * @DESCRIPTION Run python test cases for ERIClitpcli package
     * @PRE Connection to SUT
     * @PRIORITY HIGH
     */
    @TestId(id = "CXP9031124-2", title = "Run python test cases for ERIClitpyumapi package")
    @Test(groups={"CDB_REGRESSION", "ACCEPTANCE"})
    public void runERIClitpyumapiTests() {

    	pythonTestRunnerOperator.initialise();



        assertEquals(0, pythonTestRunnerOperator.execute());
    }

    /**
     * @DESCRIPTION Verify that the Python xml reports can be consumed and reported
     * @PRE Execution of test {@link #runERIClitpyumapiTests()}
     * @PRIORITY HIGH
     * @param className
     * @param name
     * @param failures
     */
    @TestId(id = "CXP9031124-3", title = "Parse xml outputs from python tests")
    @DataDriven(name = "surefire-reports")
    @Test(groups={"ACCEPTANCE"})
    public void parseNosetestsReports(@Input("classname") String className, @Input("name") String name,
            @Input("failures") List<Map<String, String>> failures, @Input("errors") List<Map<String, String>> errors,
            @Input("skipped") List<Map<String, Object>> skipped){
        logger.debug("TestCase:");
        logger.debug("    classname:" + className);
        logger.debug("    name:" + name);
        setTestcase(className + ":" + name, "");
        setTestInfo(name);
        for (Map<String, String> failure : failures) {
            fail(failure.get("type") + failure.get("message") + failure.get("text"));
        }
        for (Map<String, String> error : errors) {
            throw new Error(error.get("type"), error.get("message"), error.get("text"));
        }
        for (Map<String, Object> skip : skipped) {
            int type = (int) skip.get("type");
            if(type > 0){
                throw new SkipException("Number of tests to be skipped is: " + type);
            }
        }
    }
} 
