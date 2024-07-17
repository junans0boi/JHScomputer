import 'package:flutter/material.dart';

class OrderFormPage extends StatefulWidget {
  final String productThumbnail;
  final String productName;
  final int productQuantity;
  final double productPrice;

  OrderFormPage({
    required this.productThumbnail,
    required this.productName,
    required this.productQuantity,
    required this.productPrice,
  });

  @override
  _OrderFormPageState createState() => _OrderFormPageState();
}

class _OrderFormPageState extends State<OrderFormPage> {
  bool isDefaultAddress = true;
  bool isNewAddress = false;
  final _formKey = GlobalKey<FormState>();

  String userName = "이준환";
  String userPostalCode = "15866";
  String userAddress = "경기 군포시 산본천로 34 (산본동, 주공6단지풍양아파트)";
  String userDetailAddress = "635동 1303호";
  String userPhoneNumber = "010-9403-6854";
  String shippingRequest = "";

  @override
  Widget build(BuildContext context) {
    double totalPrice = widget.productQuantity * widget.productPrice;

    return Scaffold(
      appBar: AppBar(
        title: Text("Order Form"),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Product Details
                Row(
                  children: [
                    Image.asset(widget.productThumbnail, height: 100),
                    SizedBox(width: 16),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(widget.productName, style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                        Text("Quantity: ${widget.productQuantity}"),
                        Text("Price: ${widget.productPrice.toStringAsFixed(2)} 원"),
                      ],
                    ),
                  ],
                ),
                Divider(),
                Text("Total Price: ${totalPrice.toStringAsFixed(2)} 원", style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                Divider(),

                // Orderer Information
                Text("주문자", style: TextStyle(fontSize: 16)),
                Text("이름: 이준환", style: TextStyle(fontSize: 16)),
                Divider(),

                // Shipping Information
                Text("배송지", style: TextStyle(fontSize: 16)),
                Row(
                  children: [
                    Checkbox(
                      value: isDefaultAddress,
                      onChanged: (bool? value) {
                        setState(() {
                          isDefaultAddress = value!;
                          isNewAddress = !value;
                        });
                      },
                    ),
                    Text("기본 배송지"),
                    Checkbox(
                      value: isNewAddress,
                      onChanged: (bool? value) {
                        setState(() {
                          isNewAddress = value!;
                          isDefaultAddress = !value;
                        });
                      },
                    ),
                    Text("신규 배송지"),
                  ],
                ),
                if (isNewAddress) ...[
                  TextFormField(
                    decoration: InputDecoration(labelText: "이름"),
                    enabled: true,
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return '이름을 입력해 주세요';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "우편번호"),
                    initialValue: userPostalCode,
                    enabled: false,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "주소"),
                    initialValue: userAddress,
                    enabled: false,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      // Address search logic here
                    },
                    child: Text("검색하기"),
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "상세주소"),
                    enabled: true,
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return '상세주소를 입력해 주세요';
                      }
                      return null;
                    },
                  ),
                  Row(
                    children: [
                      Expanded(
                        child: TextFormField(
                          decoration: InputDecoration(labelText: "연락처"),
                          enabled: true,
                          initialValue: userPhoneNumber.split("-")[0],
                          validator: (value) {
                            if (value == null || value.isEmpty) {
                              return '연락처를 입력해 주세요';
                            }
                            return null;
                          },
                        ),
                      ),
                      SizedBox(width: 8),
                      Expanded(
                        child: TextFormField(
                          enabled: true,
                          initialValue: userPhoneNumber.split("-")[1],
                        ),
                      ),
                      SizedBox(width: 8),
                      Expanded(
                        child: TextFormField(
                          enabled: true,
                          initialValue: userPhoneNumber.split("-")[2],
                        ),
                      ),
                    ],
                  ),
                ] else ...[
                  TextFormField(
                    decoration: InputDecoration(labelText: "이름"),
                    initialValue: userName,
                    enabled: false,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "우편번호"),
                    initialValue: userPostalCode,
                    enabled: false,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "주소"),
                    initialValue: userAddress,
                    enabled: false,
                  ),
                  TextFormField(
                    decoration: InputDecoration(labelText: "상세주소"),
                    initialValue: userDetailAddress,
                    enabled: false,
                  ),
                  Row(
                    children: [
                      Expanded(
                        child: TextFormField(
                          decoration: InputDecoration(labelText: "연락처"),
                          initialValue: userPhoneNumber.split("-")[0],
                          enabled: false,
                        ),
                      ),
                      SizedBox(width: 8),
                      Expanded(
                        child: TextFormField(
                          initialValue: userPhoneNumber.split("-")[1],
                          enabled: false,
                        ),
                      ),
                      SizedBox(width: 8),
                      Expanded(
                        child: TextFormField(
                          initialValue: userPhoneNumber.split("-")[2],
                          enabled: false,
                        ),
                      ),
                    ],
                  ),
                ],
                Divider(),
                Text("배송 시 요청사항", style: TextStyle(fontSize: 16)),
                TextFormField(
                  onChanged: (value) {
                    shippingRequest = value;
                  },
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                  ),
                ),
                SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      // Pop-up logic with order details
                      showDialog(
                        context: context,
                        builder: (BuildContext context) {
                          return AlertDialog(
                            title: Text("Order Details"),
                            content: SingleChildScrollView(
                              child: ListBody(
                                children: [
                                  Text("Product: ${widget.productName}"),
                                  Text("Quantity: ${widget.productQuantity}"),
                                  Text("Price: ${totalPrice.toStringAsFixed(2)} 원"),
                                  Text("Shipping Address: ${isDefaultAddress ? userAddress : 'New Address'}"),
                                  Text("Shipping Request: $shippingRequest"),
                                ],
                              ),
                            ),
                            actions: [
                              TextButton(
                                child: Text("OK"),
                                onPressed: () {
                                  Navigator.of(context).pop();
                                },
                              ),
                            ],
                          );
                        },
                      );
                    }
                  },
                  child: Text("주문하기"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
