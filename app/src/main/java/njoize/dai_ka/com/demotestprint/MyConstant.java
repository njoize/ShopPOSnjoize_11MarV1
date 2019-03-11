package njoize.dai_ka.com.demotestprint;

public class MyConstant {

    //    Explicit
    private String urlGetOrderWhereTID = "http://www.brainwakecafe.com/android/getOrderWhereTID.php";

    private String urlGetOrderWhereUser = "http://www.brainwakecafe.com/android/getOrderWhereUser.php";

    private String urlGetMemberWhereID = "http://www.brainwakecafe.com/android/getMember.php";

    private String urlGetAllMember = "http://www.brainwakecafe.com/android/getAllMember.php";

    private String urlPaymentOrder = "http://www.brainwakecafe.com/android/editOrder.php";

    private String urlAddOrder = "http://www.brainwakecafe.com/android/addOrder.php";

    private int[] allowPayment = new int[]{1,3,4,6};
    private String urlGetFoodWhereIdAndUser = "http://www.brainwakecafe.com/android/getAllProduct.php";

    private int[] iconDrawerInts = new int[]{R.drawable.ic_action_one, R.drawable.ic_action_one, R.drawable.ic_action_exit, R.drawable.ic_action_one};
    private String[] titleDrawerStrings = new String[]{"Menu1", "Menu2", "Sign Out", "Test Print"};

    private String urlGetCategoryString = "http://brainwakecafe.com/android/getAllProductCat.php";

    private String[] detailStrings = new String[]{"idBill", "Time", "cnum", "type", "name", "Zone", "Desk"};

    private String ipAddressPrinter = "192.168.1.87";
    private int portPrinter = 9100;


    private String urlGetUserWhereName = "http://www.brainwakecafe.com/android/getUserWhereName.php";

    private String nameShopString = "Name Shop";

    private String[] titleTabStrings = new String[]{"Bill", "Desk", "Food", "Member"};
//    private String[] titleTabStrings = new String[]{"Bill", "Desk", "Food", "Noti"};

    private String[] billTitleStrings = new String[]{"ยังไม่ชำระ", "สำเร็จ", "ยกเลิก"};

    private int[] iconBillTitleInts = new int[]{R.drawable.ic_action_bill, R.drawable.ic_action_desk, R.drawable.ic_action_food, R.drawable.ic_action_noti};

    private String urlTestReadAllData = "https://jsonplaceholder.typicode.com/users";

    private String urlBillWhereOrder = "http://www.brainwakecafe.com/android/getAllOrder.php";

    private String urlBillDetailWhereOID = "http://www.brainwakecafe.com/android/getAllOrderDetail.php";

    private String urlBillFinishWhereOrder = "http://www.brainwakecafe.com/android/getFinishOrder.php";

    private String urlBillCancleWhereOrder = "http://www.brainwakecafe.com/android/getCancleOrder.php";

    public String getUrlBillCancleWhereOrder() {
        return urlBillCancleWhereOrder;
    }

    public String getUrlBillFinishWhereOrder() {
        return urlBillFinishWhereOrder;
    }

    public String getUrlBillDetailWhereOID() {
        return urlBillDetailWhereOID;
    }

    private String sharePreferFile = "userLogin";

    private String urlReadAllDesk = "http://www.brainwakecafe.com/android/getAllDesk.php";

//    Getter


    public String getUrlGetOrderWhereTID() {
        return urlGetOrderWhereTID;
    }

    public String getUrlGetOrderWhereUser() {
        return urlGetOrderWhereUser;
    }

    public String getUrlGetMemberWhereID() {
        return urlGetMemberWhereID;
    }

    public String getUrlGetAllMember() {
        return urlGetAllMember;
    }

    public String getUrlPaymentOrder() {
        return urlPaymentOrder;
    }

    public String getUrlAddOrder() {
        return urlAddOrder;
    }

    public int[] getAllowPayment() {
        return allowPayment;
    }

    public String getUrlGetFoodWhereIdAndUser() {
        return urlGetFoodWhereIdAndUser;
    }

    public String getUrlGetCategoryString() {
        return urlGetCategoryString;
    }

    public int[] getIconDrawerInts() {
        return iconDrawerInts;
    }

    public String[] getTitleDrawerStrings() {
        return titleDrawerStrings;
    }

    public String[] getDetailStrings() {
        return detailStrings;
    }

    public String getIpAddressPrinter() {
        return ipAddressPrinter;
    }

    public int getPortPrinter() {
        return portPrinter;
    }

    public String getUrlReadAllDesk() {
        return urlReadAllDesk;
    }

    public String getSharePreferFile() {
        return sharePreferFile;
    }

    public String getUrlBillWhereOrder() {
        return urlBillWhereOrder;
    }

    public String getUrlTestReadAllData() {
        return urlTestReadAllData;
    }

    public int[] getIconBillTitleInts() {
        return iconBillTitleInts;
    }

    public String[] getBillTitleStrings() {
        return billTitleStrings;
    }

    public String[] getTitleTabStrings() {
        return titleTabStrings;
    }

    public String getNameShopString() {
        return nameShopString;
    }

    public String getUrlGetUserWhereName() {
        return urlGetUserWhereName;
    }
}
