package com.LMS.Student.Reg.service;

import java.io.File;

public interface UploadPathService {
    File getFilePath(String modifiedFileName, String path);
}
