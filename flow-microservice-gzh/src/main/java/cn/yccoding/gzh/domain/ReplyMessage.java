package cn.yccoding.gzh.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author YC
 * @create 2020/3/7
 */
@Entity
@Data
@Table(name = "t_reply_message")
public class ReplyMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    private String text;

    private byte[] file;

}
