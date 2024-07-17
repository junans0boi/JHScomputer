import 'package:flutter/material.dart';

class Footer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
    
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            '상호: 정효성컴퓨터 | 대표: 정효성 | 개인정보관리책임자: 이준환 | 이메일: wjdgytjd2002@naver.com',
            style: TextStyle(
              color: Colors.black,
              fontSize: 14,
              fontFamily: 'GmarketSans',
              fontWeight: FontWeight.w400,
            ),
          ),
          Text(
            '사업자등록번호: 236-36-00874 | 연락처: 수리 및 개인 서비스의 등록: 컴퓨터 및 주변장비 수리업',
            style: TextStyle(
              color: Colors.black,
              fontSize: 14,
              fontFamily: 'GmarketSans',
              fontWeight: FontWeight.w400,
            ),
          ),
        ],
      ),
    );
  }
}
