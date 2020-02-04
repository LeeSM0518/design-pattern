package project.shoppingmall;

public interface MemberDao {

  Member selectOne(Member member);
  void insert(Member member);

}
