package com.example.scim_cam.utils;

import com.example.scim_cam.model.Group;

import com.example.scim_cam.model.User;
import com.example.scim_cam.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

public class JsonUtil {

    //    STATIC ATTRIBUTES: USERS
    public static final String USER_ID = "id";
    public static final String USER_UUID = "uuid";
    public static final String USER_FIRST_NAME = "first";
    public static final String USER_MIDDLE_NAME = "middle";
    public static final String USER_LAST_NAME = "last";
    public static final String USER_USERNAME = "login";
    public static final String USER_EMAIL = "email";
    public static final String USER_ICECREAM = "iceCream";
    public static final String USER_EMPNO = "employeeNumber";
    public static final String USER_CC = "costCenter";
    public static final String USER_ACTIVE = "isActive";

    //    STATIC ATTRIBUTES: GROUPS
    public static final String GROUP_ID = "id";
    public static final String GROUP_UUID = "uuid";
    public static final String GROUP_NAME = "name";
    public static final String GROUP_MENBERS = "menbers";

    //BEGIN: METHODS THAT CONVERT PAYLOAD TO JAVA OBJECT

    /**
     * Convert User to JSON
     */
    public static Map<String, Object> usersToPayLoad(List<User> users) {
        Map<String, Object> returnValue = new HashMap<>();

//        ITERATE LIST OF USERS
        List<Map> usr = new ArrayList<>();
        for (User u :
                users) {
            //CONVERT EACH USER AND ADD TO PAYLOAD
            usr.add(userToPayLoad(u));
        }
        returnValue.put("users", usr);

        return returnValue;
    } //usersToPayload

    /*
     * Convert User to JSON
     * */
    public static Map<String, Object> userToPayLoad(User user) {
        // SET FLAT ATTRIBUTES
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put(USER_ID, Integer.toString(user.getId()));
        returnValue.put(USER_UUID, user.getUuid());
        returnValue.put(USER_ACTIVE, user.getActive());
        returnValue.put(USER_USERNAME, user.getUserName());
        returnValue.put(USER_FIRST_NAME, user.getFirstName());
        returnValue.put(USER_MIDDLE_NAME, user.getMiddleName());
        returnValue.put(USER_LAST_NAME, user.getLastName());
        returnValue.put(USER_EMPNO, user.getEmployeeNumber());
        returnValue.put(USER_CC, user.getCostCenter());
        returnValue.put(USER_EMAIL, user.getEmail());
        returnValue.put(USER_ICECREAM, user.getFavoriteIceCream());
        return returnValue;
    } //userToPayload

    public static User toUser(Map<String, Object> payload) {
        //GET FLAT ATTRIBUTES
        String userName = (payload.get(USER_USERNAME) != null) ? payload.get(USER_USERNAME)
                .toString() : null;
        String fristName = (payload.get(USER_FIRST_NAME) != null) ? payload.get(USER_FIRST_NAME)
                .toString() : null;
        String middleName = (payload.get(USER_MIDDLE_NAME) != null) ? payload.get(USER_MIDDLE_NAME)
                .toString() : null;
        String lastName = (payload.get(USER_LAST_NAME) != null) ? payload.get(USER_LAST_NAME)
                .toString() : null;
        String email = (payload.get(USER_EMAIL) != null) ? payload.get(USER_EMAIL)
                .toString() : null;
        String iceCream = (payload.get(USER_ICECREAM) != null) ? payload.get(USER_ICECREAM)
                .toString() : null;
        String empNo = (payload.get(USER_EMPNO) != null) ? payload.get(USER_EMPNO).toString() : null;
        String cc = (payload.get(USER_CC) != null) ? payload.get(USER_CC).toString() : null;
        Boolean active = (payload.get(USER_ACTIVE) != null) ? (Boolean) payload.get(USER_ACTIVE) : true;
        return new User(userName, fristName, middleName, lastName, email, iceCream, empNo, cc, active);
    } //toUser

    /*
    * Convert List of Groups to JSON
    * */
    public static Map<String, Object> groupsToPayload(List<Group> groups) {
        Map<String, Object> returnValue = new HashMap<>();

        List<Map> groupList = new ArrayList<>();
        for (Group g :
                groups) {
            //CONVERT EACH GROUP AND ADD TO PAYLOAD
            groupList.add(JsonUtil.groupToPayload(g));
        }
        returnValue.put("groups", groupList);
        return returnValue;
    } // groupsToPayload

    /*
    * Convert Group to JSON
    * */
    public static Map<String, Object> groupToPayload(Group group) {
        Map<String, Object> returnValue = new HashMap<>();

        //SET FLAT ATTRIBUTES
        returnValue.put(GROUP_ID, group.getId());
        returnValue.put(GROUP_NAME, group.getDisplayName());
        returnValue.put(GROUP_UUID, group.getUuid());

        //SET GROUP MEMBERS
        List<Integer> members = new ArrayList<>();
        for (User u :
                group.getUsers()) {
            members.add(u.getId());
        }
        returnValue.put(GROUP_MENBERS, members);
        return returnValue;
    }// groupToPayload

    /**
     * Convert JSON payload to Group
     */
    public static Group toGroup(Map<String, Object> payload, UserRepository userRepository) {
        String displayName = payload.get(GROUP_NAME).toString();

        // Lấy danh sách ID của các thành viên trong nhóm từ payload
        List<Integer> memberIds = (List<Integer>) payload.get(GROUP_MENBERS);

        // Chuyển đổi từ ID thành các đối tượng User, bỏ qua bất kỳ ID nào không tìm thấy
        List<User> members = memberIds.stream()
                .map(userRepository::findById) // Sử dụng findById để lấy Optional<User>
                .filter(Optional::isPresent) // Lọc ra chỉ những Optional có giá trị
                .map(Optional::get) // Chuyển đổi từ Optional<User> thành User
                .collect(Collectors.toList()); // Thu thập vào một List

        return new Group(displayName, members);
    } // toGroup
}
