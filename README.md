# Nachive
Nachive (나카이브)

06.30
계획 수립 및 작업 

07.01
춘배 - 
네이버, 카카오 로그인 일단은 구현완료. 
토큰 관련해서는 조금 더 알아봐야 할 듯 함. 
구글 로그인은 추후 추가 예정. 
로그인 파이어베이스와는 아직 미연동.
상단바(스테이터스바) 색상 변경.
현재 실행시 시작 액티비티는 LoginActivity로 되어있습니다! 변경 필요하시면 intent-filter 옮기셔도 됩니다!
카카오 로그인은 아마 키 해시를 올려둬야 실행 가능할겁니다! 이부분은 저녁 회의때 말씀드리겠습니다.

07.02
춘배 -
구글 로그인 구현완료.
네이버, 카카오, 구글 로그인 각자 유저별 고유값으로 유저 판별할 예정. (삭제했다가 다시 가입해도 기존 정보를 사용할 수 있도록 하기 위함)
프로필 설정화면 구현했으나 Ui로직만 짜둔 상황 (이미지 불러오기는 제외)
MVVM을 좀 더 활용하기 위해서 기존 ClickListener부분을 XML로 옮겨옴 (DataBinding 활용)
앱 아이콘 변경완료. 앱 표시명 한글로 변경.
스플래시 화면 추가 및 애니메이션 적용 완료.

07.04
춘배 -
파이어베이스와 연동 완료.
프로필 설정 화면 이미지 불러오기 (갤러리 연동) 완료.
유저 프로필설정 완료시 파이어베이스 Storage, Store에 사진 및 정보 업로드 완료.
스플래시 화면에서 Firestore에서 Accept된 유저 판별 후 있으면 프로필설정 화면으로, 없으면 로그인 화면으로 보냄.
프로필 설정에서 확인버튼 무분별하게 눌리던 오류 수정.
닉네임 최대 글자수 13글자로 설정완료.

07.07
춘배 -
작성화면 XML틀 거의 구현완료.
프로필 설정시 Past, Current 모두에 올라가도록 업로드 로직 수정.
현재 갤러리, 책 들어갈 부분 틀만 제작해둔 상태 (Rv, CardView)
영화는 책과 같은 틀을 사용할 예정.
지금은 WriteActivity 내부에 포함되어있지만, 추후 따로 분리 후 <include> 사용 할 생각.
클릭시 사라지는 로직 등은 추후 추가할 예정.
   
노바 - Main 화면 상단 툴바 UI 구성 완료.
Drawer 화면 전체 UI 구성 완료.
Write 화면 이미지 해상도 이슈 수정 완료.
XML 파일 작업만 진행중.
