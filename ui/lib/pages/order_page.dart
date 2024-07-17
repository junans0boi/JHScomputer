import 'package:flutter/material.dart';

class OrderPage extends StatefulWidget {
  @override
  _OrderPageState createState() => _OrderPageState();
}

class _OrderPageState extends State<OrderPage> {
  bool isAdult = true;
  bool isFriendAdded = false;
  final TextEditingController budgetController = TextEditingController();
  final TextEditingController usageController = TextEditingController();
  final TextEditingController gameController = TextEditingController();
  final TextEditingController contactController = TextEditingController();
  final TextEditingController nameController = TextEditingController();
  final TextEditingController phone1Controller = TextEditingController();
  final TextEditingController phone2Controller = TextEditingController();
  final TextEditingController phone3Controller = TextEditingController();
  final TextEditingController postalCodeController = TextEditingController();
  final TextEditingController addressController = TextEditingController();
  final TextEditingController detailedAddressController = TextEditingController();
  bool emailConsent = false;
  bool smsConsent = false;

  void _showSummaryDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('작성 내용'),
          content: SingleChildScrollView(
            child: ListBody(
              children: [
                Text('성인 여부: ${isAdult ? '성인' : '미성년자'}'),
                Text('카카오톡 친구 추가 여부: ${isFriendAdded ? '추가됨' : '추가 안됨'}'),
                Text('견적 구매 예산: ${budgetController.text}'),
                Text('컴퓨터 사용 용도: ${usageController.text}'),
                Text('주로 하는 게임: ${gameController.text}'),
                Text('추가 연락처: ${contactController.text}'),
                Text('이름: ${nameController.text}'),
                Text('휴대폰 번호: ${phone1Controller.text} - ${phone2Controller.text} - ${phone3Controller.text}'),
                Text('우편번호: ${postalCodeController.text}'),
                Text('주소: ${addressController.text}'),
                Text('세부주소: ${detailedAddressController.text}'),
                Text('마케팅 정보 수신 동의: 이메일 ${emailConsent ? '동의' : '비동의'}, 문자 메시지 ${smsConsent ? '동의' : '비동의'}'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('확인'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('1:1 맞춤 견적 요청서 작성'),
      ),
      body: Row(
        children: [
          Expanded(
            flex: 1,
            child: SingleChildScrollView(
              padding: EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('- 구매자분께서는 미성년자이신가요?'),
                  Row(
                    children: [
                      Checkbox(
                        value: isAdult,
                        onChanged: (bool? value) {
                          setState(() {
                            isAdult = value!;
                          });
                        },
                      ),
                      Text('- 성인입니다.'),
                    ],
                  ),
                  Row(
                    children: [
                      Checkbox(
                        value: !isAdult,
                        onChanged: (bool? value) {
                          setState(() {
                            isAdult = !value!;
                          });
                        },
                      ),
                      Expanded(
                        child: Text(
                          '- 미성년자입니다. [미성년자의 경우 부모님께서 직접 견적 요청을 해주시길 부탁드립니다.]',
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 16.0),
                  Text('- 아래에서 요청하신 견적은 카카오톡으로 전송 됩니다.[필수]'),
                  Text(
                    '친구목록 -> 검색 -> "정효성컴퓨터 렌탈샵" 검색',
                    style: TextStyle(color: Colors.red),
                  ),
                  Row(
                    children: [
                      Checkbox(
                        value: isFriendAdded,
                        onChanged: (bool? value) {
                          setState(() {
                            isFriendAdded = value!;
                          });
                        },
                      ),
                      Text('- 확인 후, 친구 추가 하였습니다.'),
                    ],
                  ),
                  SizedBox(height: 16.0),
                  Text('아래 내용부터 견적 맞추는데 중요한 내용이기에 신중하고 자세히 작성 부탁드립니다.'),
                  Text('- 견적 구매 예산'),
                  TextField(
                    controller: budgetController,
                    decoration: InputDecoration(hintText: '예시) 100만원 내외'),
                  ),
                  SizedBox(height: 16.0),
                  Text('- 컴퓨터 사용 용도(컴퓨터로 주로 어떤 작업(예정)을 하시나요?)'),
                  TextField(
                    controller: usageController,
                    decoration: InputDecoration(hintText: '예시) 게임 및 영상편집(프리미어프로, 포토샵, 에프터이펙트), 설계(캐드), 사무용 캐드'),
                  ),
                  SizedBox(height: 16.0),
                  Text('- 주로 하는 게임(하시는(예정) 게임 중 가장 높은 사양의 게임을 작성해주세요.)'),
                  TextField(
                    controller: gameController,
                    decoration: InputDecoration(hintText: '예시) 리그오브레전드, 배틀그라운드, 오버워치, 레데리'),
                  ),
                  SizedBox(height: 16.0),
                  Text('- 견적 상담 연락을 못 받으셨다면 견적 요청을 다시한번 남겨주세요'),
                  TextField(
                    controller: contactController,
                  ),
                  SizedBox(height: 16.0),
                  ElevatedButton(
                    onPressed: _showSummaryDialog,
                    child: Text('컴퓨터 견적 요청하기'),
                  ),
                ],
              ),
            ),
          ),
          VerticalDivider(),
          Expanded(
            flex: 1,
            child: SingleChildScrollView(
              padding: EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('이름'),
                  TextField(
                    controller: nameController,
                    decoration: InputDecoration(hintText: '이름을 작성해주세요'),
                    enabled: false,
                  ),
                  SizedBox(height: 16.0),
                  Text('휴대폰 번호'),
                  Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: phone1Controller,
                          decoration: InputDecoration(hintText: '010'),
                          enabled: false,
                        ),
                      ),
                      Text(' - '),
                      Expanded(
                        child: TextField(
                          controller: phone2Controller,
                          enabled: false,
                        ),
                      ),
                      Text(' - '),
                      Expanded(
                        child: TextField(
                          controller: phone3Controller,
                          enabled: false,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 16.0),
                  Text('우편번호'),
                  TextField(
                    controller: postalCodeController,
                    decoration: InputDecoration(hintText: '우편번호'),
                    enabled: false,
                  ),
                  SizedBox(height: 16.0),
                  Text('주소'),
                  TextField(
                    controller: addressController,
                    decoration: InputDecoration(hintText: '주소'),
                    enabled: false,
                  ),
                  SizedBox(height: 16.0),
                  TextField(
                    controller: detailedAddressController,
                    decoration: InputDecoration(hintText: '세부주소'),
                    enabled: false,
                  ),
                  SizedBox(height: 16.0),
                  Text('마케팅 정보 수신 동의'),
                  Row(
                    children: [
                      Checkbox(
                        value: emailConsent,
                        onChanged: (bool? value) {
                          setState(() {
                            emailConsent = value!;
                          });
                        },
                      ),
                      Text('이메일'),
                      Checkbox(
                        value: smsConsent,
                        onChanged: (bool? value) {
                          setState(() {
                            smsConsent = value!;
                          });
                        },
                      ),
                      Text('문자 메시지'),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

void main() {
  runApp(MaterialApp(
    home: OrderPage(),
  ));
}
