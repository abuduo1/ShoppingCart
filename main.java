package bigWork;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("rawtypes")
class Product implements Comparable{

    private Integer id;
    private String name;
    private double price;


    public Product() {
    }

    public Product(Integer id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return this.id/10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "商品编号：" + id +
                ", 商品名称：" + name + '\'' +
                ", 商品单价：" + price +
                '}'+"\n";
    }

    @Override
    public int compareTo(Object o) {
        Product product = (Product)o;
        int this_id = this.id;
        int p_id = product.getId();
        return this_id > p_id?1:-1;
    }
}



class ShoppingCar  {

    private Map<Product,Integer> cars;
    private double totalPrice;

    public ShoppingCar(){

        cars = new HashMap<>();

    }

    //对外提供添加多个一类商品的方法
    public void addProducts(Product p,Integer num){
        if (num<=0){
            System.out.println("商品数量不能小于1！");
            return;
        }

        if (cars.containsKey(p)){
            //如果能执行到这里说明购物车已经包含此商品了 这时需要累加 商品的数量
            int productcount_before = cars.get(p);
            int productcount_after = productcount_before + num;
            cars.put(p,productcount_after);
            System.out.println("添加商品成功！");
        }else {
            //程序执行到这里说明购物车中没有此商品 则添加
            cars.put(p,num);
            System.out.println("添加商品成功！");
        }
        double price = (p.getPrice())*num;
        totalPrice = totalPrice + price;
    }

    //提供添加一个商品的方法
    public void addProducts(Product product){
        addProducts(product,1);
        System.out.println("添加一个商品成功！");
    }

    //删除一类商品(指定数量)
    public void removeProducts(Product product,Integer deletenum){
        if (!cars.containsKey(product)){
            System.out.println("您要删除的商品不存在！");
            return;
        }
        //获取这个商品此时的数量 要删除的数量不能大于这个数量
        int productNum = cars.get(product);
        if (deletenum>productNum){
            System.out.println("删除的商品数量不能大于已有的数量!");
            return;
        }
        //程序能执行到这里说明满足上面的条件
        //key重复 value覆盖
        int afterDelete = productNum - deletenum;
        //判断删除的数量是否的存在的数量相等 如果相等 则应连同商品删除
        if (productNum == deletenum){
            cars.remove(product);
            System.out.println("删除商品成功！");
        }else {
            cars.put(product,afterDelete);
            System.out.println("删除商品成功！");
        }
        //删去对应的价格
        double price = (product.getPrice()) * deletenum;
        totalPrice = totalPrice - price;
    }

    //某类商品全部删除
    public void deleteClassProduct(Product product){
        if (!cars.containsKey(product)){
            System.out.println("该类商品不存在！");
            return;
        }
        cars.remove(product);
        System.out.println("该类删除商品成功！");
    }

    //删除某类商品其中的一个
    public void deleteAproduct(Product product){
        this.removeProducts(product,1);
    }

    //清空购物车
    public void clearShoppingCar(){
        if (cars.size()<=0){
            System.out.println("您的购物车里没有任何商品哦^-^,请快去加几件宝贝进库吧！"+"\n");
            return;
        }
        cars.clear();
        totalPrice = 0.0;
        System.out.println("清空购物车成功！");
    }

    //结账
    public void checkOut() throws Exception{
        if (cars.size()<=0){
            System.out.println("您的购物车里没有任何商品哦~"+"\n");
            return;
        }else {
            System.out.println("正在结账请稍后··········");
            Thread.sleep(3000);
            StringBuilder sb = new StringBuilder();

            sb.append("\t\t购物详单："+"\n\n");
            sb.append("_______________________________________________________________________________\n");
            for (Map.Entry<Product,Integer> me : cars.entrySet()){
                int num = me.getValue();
                Product product = me.getKey();
                int pid = product.getId();
                String pname = product.getName();
                double price = product.getPrice();

                sb.append("\t\t商品编号："+pid+"\t商品名称："+pname+"\t商品单价："+price+"\t商品数量："+num+"\n");
            }
            sb.append("\n");
            sb.append("\t\t\t\t\t总价："+totalPrice+"\n\n");
            sb.append("\t\t祝您购物愉快，欢迎下次光临！");
            System.out.println(sb.toString());
            clearShoppingCar();
        }
    }

    //查看购物车
    public void lookCars(){
        if (cars.size()<=0){
            System.out.println("您的购物车里没有任何商品哦~"+"\n");
            return;
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append("\t\t您的购物车如下>>>>>>："+"\n\n");
            for (Map.Entry<Product,Integer> me : cars.entrySet()){
                int num = me.getValue();
                Product product = me.getKey();
                int pid = product.getId();
                String pname = product.getName();
                double price = product.getPrice();

                sb.append("\t\t商品编号："+pid+"\t商品名称："+pname+"\t商品单价："+price+"\t商品数量："+num+"\n");
            }
            System.out.println(sb.toString());
        }
    }

    public Map<Product, Integer> getCars() {
        return cars;
    }

    public void setCars(Map<Product, Integer> cars) {
        this.cars = cars;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Shoppcar{" +
                "cars=" + cars +
                ", totalPrice=" + totalPrice +
                '}';
    }

   public void show(){
    System.out.println("1.potato\t5.6\n2.milk  \t7.8\n"+
			"3.chips  \t3.2\n4.juice  \t3.5\n");
   }
}
   
public class ShoppingCart extends Application{
	Pane pane = new Pane();  
	ShoppingCar shoppcart = new ShoppingCar();
	Product[] products = {new Product(1,"potato",5.6),new Product(2,"milk",7.8),
			new Product(3,"chips",3.2),new Product(4,"juice",3.5)};
    
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {		
		shoppcart.show();		
		double width = 500;
		double height = 250;
		
		Text text = new Text("Are you ready for shopping（Y/N）\r\n");
		text.setX(50);
		text.setY(50);
		Button button = new Button("Yes");
		button.setLayoutX(100);
		button.setLayoutY(200);
		Button button2 = new Button("NO");
		button2.setLayoutX(200);
		button2.setLayoutY(200);
		pane.getChildren().addAll(text,button,button2);
		
		button.setOnAction(e->{
			pane.getChildren().clear();
			pane.getChildren().add(new startShoppingPane());
		});
		
		button2.setOnAction(e->{
			pane.getChildren().clear();
			Text text2 = new Text("Shopping is over! Bye !");
			text2.setX(50);
			text2.setY(150);
			pane.getChildren().add(text2);
		});
		
		Scene scene = new Scene(pane, width, height);
		primaryStage.setTitle("ShoppingCart");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	class startShoppingPane extends Pane{
				
		public startShoppingPane() {        
		GridPane gridpane = new GridPane();		
		gridpane.setPadding(new Insets(15,15,15,15));				

		Button bt1 = new Button("√");
		Button bt2 = new Button("√");
		Button bt3 = new Button("√");
		Button bt4 = new Button("√");
		Button bt5 = new Button("√");

		gridpane.add(new Text("Enter your option:"), 0, 0);
		gridpane.add(new Text("1:Add product"), 0, 1);
		gridpane.add(bt1, 1, 1);
		gridpane.add(new Text("2:Delete product"), 0, 2);
		gridpane.add(bt2, 1, 2);
		gridpane.add(new Text("3:clear the shopping cart"), 0, 3);
		gridpane.add(bt3, 1, 3);
		gridpane.add(new Text("4:check the products in shopping cart"), 0, 4);
		gridpane.add(bt4, 1, 4);
		gridpane.add(new Text("5:show the total ammount"), 0, 5);
		gridpane.add(bt5, 1, 5);
		
		getChildren().add(gridpane);
		
		bt1.setOnAction(e->{
			gridpane.getChildren().clear();
			gridpane.add(new Text("Which one you want to add to shopping cart?"), 0, 0);
			gridpane.add(new Text("The product you want:(input numbers)"), 0, 1);
			TextField tfnumber = new TextField();
			gridpane.add(tfnumber, 1, 1);
			gridpane.add(new Text("The number you want:(input numbers)"), 0, 2);
			TextField tfmuch = new TextField();
			gridpane.add(tfmuch, 1, 2);
						
			Button Return=new Button("OK");
			Return.setTextFill(Color.GREEN);
			gridpane.add(Return, 1, 3);
			
			Return.setOnAction(ee->{
				Product p= getProduct(Integer.parseInt(tfnumber.getText()), products);
				shoppcart.addProducts(p,Integer.parseInt(tfmuch.getText()));
				gridpane.getChildren().clear();
		        gridpane.getChildren().add(new startShoppingPane());
			});
          
		});	
		
		bt2.setOnAction(e->{
			gridpane.getChildren().clear();
			gridpane.add(new Text("Which one you want to delete in shopping cart?"), 0, 0);
			gridpane.add(new Text("The product you want:(input numbers)"), 0, 1);
			TextField tfnumber = new TextField();
			gridpane.add(tfnumber, 1, 1);
			gridpane.add(new Text("The number you want:(input numbers)"), 0, 2);
			TextField tfmuch = new TextField();
			gridpane.add(tfmuch, 1, 2);
			gridpane.add(new Button("OK"), 1, 3);

			Button Return=new Button("OK");
			Return.setTextFill(Color.GREEN);
			gridpane.add(Return, 1, 3);
			
			Return.setOnAction(ee->{
				Product p= getProduct(Integer.parseInt(tfnumber.getText()), products);
				shoppcart.removeProducts(p,Integer.parseInt(tfmuch.getText()));
				gridpane.getChildren().clear();
				gridpane.getChildren().add(new startShoppingPane());
			});
			
		});
		
		
		bt3.setOnAction(e->{
			gridpane.getChildren().clear();
			shoppcart.clearShoppingCar();	
			
			gridpane.getChildren().clear();
			gridpane.getChildren().add(new startShoppingPane());
		});
		
		bt4.setOnAction(e->{
			gridpane.getChildren().clear();
			shoppcart.lookCars();	
			
			gridpane.getChildren().clear();
			gridpane.getChildren().add(new startShoppingPane());
		});
		
		bt5.setOnAction(e->{
			gridpane.getChildren().clear();
			try {
				shoppcart.checkOut();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			gridpane.getChildren().clear();
			gridpane.getChildren().add(new startShoppingPane());
		});
	 }
		
		
	}


	public Product getProduct(int parseInt, Product[] products) {
		return null;
	}

	
}
