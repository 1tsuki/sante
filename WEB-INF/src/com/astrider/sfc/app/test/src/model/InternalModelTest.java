package com.astrider.sfc.app.test.src.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.astrider.sfc.src.model.InternalModel;

public class InternalModelTest {
    @Test
    public void execDailyBatch() {
        InternalModel internalMock = mock(InternalModel.class);
        internalMock.execDailyBatch();
    }
}
