package vvr.mybatis.pojo;

import java.util.Date;
import java.util.List;

public class Orders {
    private Integer id;

    private Integer user_Id;

    private String number;

    private Date createtime;

    private String note;
    
    //使用resultMap完成一对一映射，需要在类中定义变量,关联用户信息
    private User user;
    
    //使用resultMap完成一对多的映射，关联订单明细
    private List<Orderdetail> orderdetails;
    

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
    
}