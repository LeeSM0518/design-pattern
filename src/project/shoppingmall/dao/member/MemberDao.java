package project.shoppingmall.dao.member;

import project.shoppingmall.dto.Member;

public interface MemberDao {

  Member selectOne(Member member);
  int insert(Member member);

}
