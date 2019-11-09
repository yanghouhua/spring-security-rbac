package com.anan.rbac.model;

/**
 * 菜单实体类（资源）
 */
public class BaseMenu {

    private String id;
    private String menuUrl;
    private String menuSeq;
    private Integer menuParentId;
    private String menuName;
    private String menuIcon;
    private String menuOrder;

    /**
     * 1 是 0 否
     */
    private String isLeaf;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return MENU_URL
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * @return MENU_SEQ
     */
    public String getMenuSeq() {
        return menuSeq;
    }

    /**
     * @param menuSeq
     */
    public void setMenuSeq(String menuSeq) {
        this.menuSeq = menuSeq;
    }

    /**
     * @return MENU_PARENT_ID
     */
    public Integer getMenuParentId() {
        return menuParentId;
    }

    /**
     * @param menuParentId
     */
    public void setMenuParentId(Integer menuParentId) {
        this.menuParentId = menuParentId;
    }

    /**
     * @return MENU_NAME
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return MENU_ICON
     */
    public String getMenuIcon() {
        return menuIcon;
    }

    /**
     * @param menuIcon
     */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    /**
     * @return MENU_ORDER
     */
    public String getMenuOrder() {
        return menuOrder;
    }

    /**
     * @param menuOrder
     */
    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    /**
     * 获取1 是 0 否
     *
     * @return IS_LEAF - 1 是 0 否
     */
    public String getIsLeaf() {
        return isLeaf;
    }

    /**
     * 设置1 是 0 否
     *
     * @param isLeaf 1 是 0 否
     */
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
}