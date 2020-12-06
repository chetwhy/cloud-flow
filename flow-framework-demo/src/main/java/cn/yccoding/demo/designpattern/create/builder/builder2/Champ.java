package cn.yccoding.demo.designpattern.create.builder.builder2;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 英雄
 *
 * @author YC
 * @since 2020/12/2
 */
@Data
public class Champ {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 召唤师技能
     */
    private List<String> sums;

    /**
     * 被动技能
     */
    private String passive;

    /**
     * 角色
     */
    private int roleId = 0;

    private Champ(String nickname, List<String> sums, String passive, int roleId) {
        this.nickname = nickname;
        this.sums = sums;
        this.passive = passive;
        this.roleId = roleId;
    }

    /**
     * 私有构造器，只能通过建造者创建
     */
    private Champ() {
    }

    public static ChampBuilder builder() {
        return new ChampBuilder();
    }

    /**
     * 建造者
     */
    public static class ChampBuilder {

        private String nickname;
        private List<String> sums;
        private String passive;
        private int roleId = 0;

        public ChampBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public ChampBuilder setSums(String... sums) {
            this.sums = Arrays.asList(sums);
            return this;
        }

        public ChampBuilder setPassive(String passive) {
            this.passive = passive;
            return this;
        }

        public ChampBuilder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        /**
         * 创建
         */
        public Champ build() {
            // TODO 校验参数...
            if (nickname == null || "".equalsIgnoreCase(nickname)) {
                throw new RuntimeException("名称不可以为空");
            }
            // 构造实例
            Champ champ = new Champ();
            champ.setNickname(nickname);
            if (sums != null && sums.isEmpty()) {
                champ.setSums(sums);
            }
            if (passive != null && "".equalsIgnoreCase(passive)) {
                champ.setPassive(passive);
            }
            if (roleId > 0) {
                champ.setRoleId(roleId);
            }
            return champ;
        }
    }
}
