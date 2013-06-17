package com.astrider.sfc.test.app.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.astrider.sfc.app.model.InternalModel;

public class InternalModelTest {
    @Test
    public void execDailyBatch() {
        InternalModel internalMock = mock(InternalModel.class);
        internalMock.execDailyBatch();
    }
}
