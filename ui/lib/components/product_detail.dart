import 'package:flutter/material.dart';
import 'order_form.dart'; // product_detail.dart 파일을 불러옴


class ProductDetailPage extends StatefulWidget {
  @override
  // _ProductDetailPageState를 셍성
  _ProductDetailPageState createState() => _ProductDetailPageState();
}


class _ProductDetailPageState extends State<ProductDetailPage> {
  // 현재 선택된 이미지 경로를 저장하는 변수
  String selectedImage = 'assets/computer/ABKO_M2000_NOVA_ARGB_강화유리_블랙.png'; // Default large image

  // 제품 이미지 경로들의 배열.
  // 썸네일 이미지와 선택된 이미지를 표시하는 데 사용
  final List<String> imagePaths = [
    'assets/computer/ABKO_M2000_NOVA_ARGB_강화유리_블랙.png',
    'assets/computer/ABKO_NCORE_G30_트루포스_화이트.png',
    'assets/computer/ABKO_NCORE_G30_트루포스_미들타워.png',
  ];

  // 상품 제목
  final String productName = '228만원 게이밍 최강 견적';
  // 상품 가격
  final String productPrice = '2,289,000원';
  // 상품 상세 설명
  final String productDescription = '완전 컴퓨터가 아닌 부품의 변경이나 케이스 변경 등 수정하여 구매하고 싶으신 분들은 상담 우측에 1:1 맞춤 견적 요청을 해주시면 나만의 컴퓨터 구매가 가능합니다.';
  // 배송비
  final String shippingCost = '무료';
  // 상품 판매 상태
  final String productStatus = '품절된 상품입니다.';

  // computerSpecs의 역활 : 현재 상품에 들어가는 컴퓨터 부품을 표로 저장
  final List<Map<String, String>> computerSpecs = [
    {'item': 'CPU', 'description': '[INTEL] 코어13세대 I5-13400F 벌크', 'price': '150,000원'},
    {'item': '메인보드', 'description': '[ASRock] B760M-HDV/M.2 D5 에즈윈', 'price': '120,000원'},
    {'item': 'RAM', 'description': '[삼성전자] 삼성 DDR5 16GB PC5-38400', 'price': '60,000원'},
    {'item': 'SSD', 'description': '[SK hynix] Gold P31 M.2 NVMe 2280 [1TB TLC]', 'price': '56,000원'},
    {'item': '그래픽카드 [GPU]', 'description': '[Colorful] iGame GeForce RTX 3060 Ti ULTRA OC White D6X 8GB', 'price': '560,000원'},
    {'item': 'CPU쿨러 [CPU_Cooler]', 'description': '[JIUSHARK] JF100RS Crystal Auto RGB (WHITE)', 'price': '23,000원'},
    {'item': '파워 서플라이 [Power]', 'description': '[Topower] Guardian TOP-700DG 80PLUS BRONZE', 'price': '79,000원'},
    {'item': '케이스 [Case]', 'description': '[DAVEN] D6 MESH 강화유리 (화이트)', 'price': '32,000원'},
    {'item': '조립비', 'description': '', 'price': '30,000원'},
    {'item': '', 'description': '총 가격', 'price': '2,289,000원'},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // SingleChildScrollView : 위젯은 스크롤이 가능한 단일 자식을 가지도록 해준디.
      body: SingleChildScrollView(
        child: Column(
          children: [
            // 영역 1: Product Image and Thumbnails
            Padding(
              // EdgeInsets.all : 전체여백을 지정하는 옵션
              padding: const EdgeInsets.all(16.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Expanded : 상세 정보 영역의 왼쪽과 오른쪽 부분을 각각 Expanded 위젯으로 나누어 공간을 차지
                  Expanded(
                    flex: 2,
                    child: Column(
                      children: [
                        Image.asset(selectedImage, height: 300),
                        SizedBox(height: 10),
                        Wrap(
                          spacing: 10,
                          children: imagePaths.map((path) {
                            // 터치 이벤트 감지
                            return GestureDetector(
                              onTap: () {
                                setState(() {
                                  selectedImage = path;
                                });
                              },
                              child: Image.asset(path, height: 50),
                            );
                          }).toList(),
                        ),
                      ],
                    ),
                  ),
                  Expanded(
                    flex: 3,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 16.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(productName, style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
                          SizedBox(height: 10),
                          Text(productPrice, style: TextStyle(fontSize: 20, color: Colors.red)),
                          SizedBox(height: 10),
                          Text(productDescription),
                          SizedBox(height: 10),
                          Text('배송비: $shippingCost'),
                          SizedBox(height: 10),
                          Text(productStatus, style: TextStyle(color: Colors.red)),
                          SizedBox(height: 10),
                          Row(
                            children: [
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => OrderFormPage(
                                        productThumbnail: selectedImage,
                                        productName: productName,
                                        productQuantity: 1,
                                        productPrice: 2289000,
                                      ),
                                    ),
                                  );
                                },
                                child: Text('구매하기'),
                              ),
                              SizedBox(width: 10),
                              OutlinedButton(
                                onPressed: () {
                                  // 장바구니에 담기 버튼 동작
                                },
                                child: Text('장바구니에 담기'),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Divider(),
            // 영역 2: 상세 정보
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Expanded(
                    flex: 1,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('컴퓨터 견적', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                        SizedBox(height: 10),
                        Table(
                          border: TableBorder.all(color: Colors.black),
                          columnWidths: {
                            0: FlexColumnWidth(2),
                            1: FlexColumnWidth(4),
                            2: FlexColumnWidth(2),
                          },
                          children: computerSpecs.map((spec) {
                            return _buildTableRow(spec['item']!, spec['description']!, spec['price']!);
                          }).toList(),
                        ),
                      ],
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('가능한 게임 및 작업', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                        SizedBox(height: 10),
                        // 여기에 가능한 게임 및 작업 내용을 추가합니다.
                        // 추후에 데이터베이스에서 데이터를 받아와서 여기에 채워넣을 수 있습니다.
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  // _buildTableRow 메소드는 TableRow을 생성한다.
  TableRow _buildTableRow(String item, String description, String price) {
    return TableRow(
      children: [
        Padding(
          // Padding : 텍스트에 패딩을 추가하여 테이블 셀의 내용을 배치
          padding: const EdgeInsets.all(8.0),
          // Text(item) : 항목 이름을 표시합니다.
          child: Text(item),
        ),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(description),
        ),
        Padding(
          padding: const EdgeInsets.all(8.0),
          // Text(price, textAlign: TextAlign.right) : 가격 오른쪽 정렬
          child: Text(price, textAlign: TextAlign.right),
        ),
      ],
    );
  }
}
