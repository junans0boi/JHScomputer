import 'package:flutter/material.dart';

class Header extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          color: Colors.white,
          height: 30, // 높이 조정
          padding: EdgeInsets.symmetric(horizontal: 20.0, vertical: 0.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  IconButton(
                    icon: Image.asset(
                      'assets/instagram_icon.png',
                      width: 24, // 이미지 크기 조정
                      height: 24, // 이미지 크기 조정
                    ),
                    iconSize: 24, // 아이콘 크기 조정
                    onPressed: () {
                      // TODO: Add your onPressed code here!
                    },
                  ),
                  IconButton(
                    icon: Image.asset(
                      'assets/youtube_icon.png',
                      width: 24, // 이미지 크기 조정
                      height: 24, // 이미지 크기 조정
                    ),
                    iconSize: 24, // 아이콘 크기 조정
                    onPressed: () {
                      // TODO: Add your onPressed code here!
                    },
                  ),
                  IconButton(
                    icon: Image.asset(
                      'assets/kakao_icon.png',
                      width: 24, // 이미지 크기 조정
                      height: 24, // 이미지 크기 조정
                    ),
                    iconSize: 24, // 아이콘 크기 조정
                    onPressed: () {
                      // TODO: Add your onPressed code here!
                    },
                  ),
                ],
              ),
              Row(
                children: [
                  TextButton(
                    onPressed: () {
                      // 동작
                    },
                    child: Text(
                      '로그인',
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 12,
                        fontFamily: 'GmarketSans',
                        fontWeight: FontWeight.w400,
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
        Container(
          color: Colors.black,
          height: 50,
          padding: EdgeInsets.symmetric(vertical: 10.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              GestureDetector(
                onTap: () {
                  // TODO: Navigate to main page
                },
                child: Image.asset(
                  'assets/logo.png', // Replace with your logo asset path
                  height: 30,
                ),
              ),
              HeaderMenuItem(title: '공지사항', onTap: () {}),
              HeaderMenuItem(title: '컴퓨터 추천', onTap: () {}),
              HeaderMenuItem(title: '주문 요청', onTap: () {}),
              HeaderMenuItem(title: '고객센터', onTap: () {}),
            ],
          ),
        ),
      ],
    );
  }
}

class HeaderMenuItem extends StatelessWidget {
  final String title;
  final VoidCallback? onTap;

  HeaderMenuItem({required this.title, this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Text(
        title,
        style: TextStyle(
          color: Colors.white,
          fontFamily: 'GmarketSans',
          fontWeight: FontWeight.w500,
        ),
      ),
    );
  }
}
