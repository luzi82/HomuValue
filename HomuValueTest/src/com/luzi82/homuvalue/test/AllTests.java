package com.luzi82.homuvalue.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ T000_Value.class, T001_IntegerValue.class, T002_FloatValue.class, T003_Remote.class, T004_Hv.class })
public class AllTests {

}
