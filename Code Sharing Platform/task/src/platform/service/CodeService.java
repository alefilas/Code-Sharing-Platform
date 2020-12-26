package platform.service;

import platform.entity.Code;

import java.util.List;
import java.util.Map;

public interface CodeService {

    Code getCodeSnippet(String id);

    Map<String, String> addNewCode(Code code);

    List<Code> getAllCodes();
}
