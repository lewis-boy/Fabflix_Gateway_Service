package edu.uci.ics.luisae.service.gateway.Base;



public enum RequestType {

    IDM_REGISTER (Tag.POST, false, false),
    IDM_LOGIN (Tag.POST, false, false),
    IDM_SESSION(Tag.POST, false, false),
    IDM_PRIVILEGE (Tag.POST, false, false),

    MOVIES_SEARCH(Tag.GET,true,false),
    MOVIES_BROWSE(Tag.GET,true,false),
    MOVIES_GET(Tag.GET,false,true),
    MOVIES_THUMBNAIL(Tag.POST,false,false),
    MOVIES_PEOPLE(Tag.GET,true,false),
    MOVIES_PEOPLE_SEARCH(Tag.GET,true,false),
    MOVIES_PEOPLE_GET(Tag.GET,false,true),

    BILLING_CART_INSERT(Tag.POST,false,false),
    BILLING_CART_UPDATE(Tag.POST,false,false),
    BILLING_CART_DELETE(Tag.POST,false,false),
    BILLING_CART_RETRIEVE(Tag.POST,false,false),
    BILLING_CART_CLEAR(Tag.POST,false,false),
    BILLING_ORDER_PLACE(Tag.POST,false,false),
    BILLING_ORDER_RETRIEVE(Tag.POST,false,false),
    BILLING_ORDER_COMPLETE(Tag.GET,false,false);



    private final int tag;
    private final boolean hasQueryParams;
    private final boolean hasTemplate;

    RequestType(int tag, boolean hasQueryParams, boolean hasTemplate){
        this.tag = tag;
        this.hasQueryParams = hasQueryParams;
        this.hasTemplate = hasTemplate;
    }

    public boolean isHasTemplate() {
        return hasTemplate;
    }

    public boolean isHasQueryParams() {
        return hasQueryParams;
    }

    public int getTag() {
        return tag;
    }
}
