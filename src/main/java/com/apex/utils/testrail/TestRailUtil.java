package com.apex.utils.testrail;

import com.apex.config.FrameworkException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.apex.config.Constants.*;

public class TestRailUtil {
    private static final APIClient CLIENT = new APIClient(TESTRAIL_ENGINE_URL);

    static {
        CLIENT.setUser(TESTRAIL_USERNAME);
        CLIENT.setPassword(TESTRAIL_PASSWORD);
    }

    public static void addResultTestCase(CaseModel caseModel) throws FrameworkException, IOException {
        // Use a generic HashMap<String, Object> for type safety
        final Map<String, Object> data = new HashMap<>();
        data.put("status_id", caseModel.getStatusId());
        data.put("comment", caseModel.getComment());

        final String endpoint = String.format("add_result_for_case/%s/%s", TEST_RUN_ID, caseModel.getCaseId());

        // We might log or handle the response
        CLIENT.sendPost(endpoint, data);
    }
}
