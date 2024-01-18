package FinalPrjTest.FinalPrj.domain;

/*
    이용자의 정보를 받아와서 작성을 하되 이용자가 현재의 상태를 변경한다면 이 변경된 사항은 원래의 이용자의 정보에 수정처리가 되어야 한다
    이거 안쓸듯 기존에 있던 updateCutomer에 pw가 null인 경우를 추가해서 그냥 같은거 써도 될거 같음
    삭제할 가능성 농후함 이거 이용해서 새로 쿼리까지 만들고 별난리 다 치다가 다른코드 재활용 가능한거 보고 안쓰기로 함 시간 벼렸음
  */
public class CustomerDataChangeInReservation {
    private String address;
    private String email;
    private String phone_num;
    private int hair_condition;
    private int hair_length;
    private String head_shape;
    private int dye_times;
    private int perm_times;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public int getHair_condition() {
        return hair_condition;
    }

    public void setHair_condition(int hair_condition) {
        this.hair_condition = hair_condition;
    }

    public int getHair_length() {
        return hair_length;
    }

    public void setHair_length(int hair_length) {
        this.hair_length = hair_length;
    }

    public String getHead_shape() {
        return head_shape;
    }

    public void setHead_shape(String head_shape) {
        this.head_shape = head_shape;
    }

    public int getDye_times() {
        return dye_times;
    }

    public void setDye_times(int dye_times) {
        this.dye_times = dye_times;
    }

    public int getPerm_times() {
        return perm_times;
    }

    public void setPerm_times(int perm_times) {
        this.perm_times = perm_times;
    }
}
