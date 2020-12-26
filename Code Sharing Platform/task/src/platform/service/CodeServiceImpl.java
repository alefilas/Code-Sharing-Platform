package platform.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.entity.Code;
import platform.repository.CodeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepository codeRepository;


    @Override
    public Code getCodeSnippet(String id) {
        Optional<Code> optionalCode = codeRepository.findByUuid(id);
        if (optionalCode.isEmpty() || checkCodeSnippetAccess(optionalCode.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Code code = optionalCode.get();

        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        if (methodName.equals("getApiCodeById")) {
            code.setViews();
            code.setTime();
        }

        return code;
    }

    @Override
    public Map<String, String> addNewCode(Code code) {
        Map<String, String> map = new HashMap<>();
        code.initDateAndTime();
        String id = codeRepository.save(code).getUuid();
        map.put("id", id);
        return map;
    }

    @Override
    public List<Code> getAllCodes() {
        List<Code> codes = codeRepository.findTop10ByTimeIsNullAndViewsIsNullOrderByDateDesc();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        codes = codes.stream().peek(code -> {
            if (methodName.equals("getApiAllCodes")) {
                code.setViews();
                code.setTime();
            }
        }).collect(Collectors.toList());
        return codes;
    }

    private boolean checkCodeSnippetAccess(Code code) {

        boolean isViewsOver = checkCodeViews(code);
        boolean isTimeIsOver = checkCodeTime(code);

        if (isTimeIsOver || isViewsOver) {
            codeRepository.delete(code);
        }

        return isTimeIsOver;
    }

    private boolean checkCodeTime(Code code) {

        Long time = code.getTime();

        if (time == null) {
            return false;
        }

        if (code.getTime() != 0) {

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime codeAccessTime = code.getDateTime().plusSeconds(code.getTime());

            if (codeAccessTime.isBefore(currentTime)) {
                return true;
            }

            code.setTime(ChronoUnit.SECONDS.between(currentTime, codeAccessTime));

        }
        return false;
    }

    private boolean checkCodeViews(Code code) {

        Integer views = code.getViews();

        if (views == null) {
            return false;
        }

        code.setViews(--views);

        if (views > 0) {
            codeRepository.save(code);
            return false;
        } else {
            return true;
        }
    }
}
