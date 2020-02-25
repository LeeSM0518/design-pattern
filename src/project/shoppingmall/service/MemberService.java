package project.shoppingmall.service;

import project.shoppingmall.dao.member.MemberDao;
import project.shoppingmall.dto.Member;

import java.util.Map;

public class MemberService {

  private MemberDao memberDao;

  public MemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public void signIn(Map<String, Object> map) {
    Member memberBeforeSignIn = new Member();
    memberBeforeSignIn.setUsername((String) map.get("username"));
    memberBeforeSignIn.setPassword((String) map.get("password"));
    Member memberAfterSignIn = memberDao.selectOne(memberBeforeSignIn);
    if (memberAfterSignIn.getName() != null) {
      map.put("accessMember", memberAfterSignIn);
    }
  }

  public void signUp(Map<String, Object> map) {
    Member signUpMember = new Member(
        (String) map.get("name"),
        (String) map.get("username"),
        (String) map.get("password"),
        (String) map.get("phoneNumber"),
        (String) map.get("address")
    );
    memberDao.insert(signUpMember);
  }

}
