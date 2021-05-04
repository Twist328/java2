package ru.progwards.java2.lessons.less5;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.ErrorManager;

import static org.junit.Assert.*;

public class CreateTmpFileTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Test(expected = IOException.class)
    public void createFileTest() throws IOException {
        boolean result;
        File randomDir = tempFolder.newFolder("random");
        File data = new File(randomDir, "ra?ndom.data");
        result = data.createNewFile();
        assertTrue("Не удалось создать файл random.data", result);
    }
}

