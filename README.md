
<h1 align="center">
  <br>
  <a href="https://github.com/Yoonjin-Lee/webtoon"><img src="https://github.com/user-attachments/assets/b73becfd-f58b-4997-b3cf-f1c0c7f6b889" alt="MyToons" width="200"></a>
  <br>
   내 취향만 모은 MyToons
  <br>
</h1>

<h4 align="center">하나의 앱에서, 여러 플랫폼의 웹툰을 요일 별로 모아보세요!</h4>
<h4 align="center">You can enjoy webtoons from various platforms in one app!</h4>

<p align="center">
  <img src="https://img.shields.io/badge/android-34A853?style=flat-square&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/firebase-DD2C00?style=flat-square&logo=firebase&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/hilt-D9D9D9?style=flat-square&logo=hilt&logoColor=white"/>
  <img src="https://img.shields.io/badge/compose-D9D9D9?style=flat-square&logo=compose&logoColor=white"/>
  <img src="https://img.shields.io/badge/flow-D9D9D9?style=flat-square&logo=flow&logoColor=white"/>
  <img src="https://img.shields.io/badge/MVVM-D9D9D9?style=flat-square&logo=MVVM&logoColor=white"/>
  <img src="https://img.shields.io/badge/Coroutine-D9D9D9?style=flat-square&logo=Coroutine&logoColor=white"/>
</p>

![explore webtoon](https://github.com/user-attachments/assets/33d91cc1-853c-4d43-a208-6614148379da)

## Key Features

웹툰을 좋아하는 사람이라면 요일별로 꼭 챙겨보는 웹툰이 있을 것입니다.  
여러 플랫폼을 오가며 웹툰을 찾아보는 과정이 번거롭게 느껴진 적은 없으신가요?  
이런 불편함을 해결하고자, 한곳에서 편리하게 웹툰을 즐길 수 있는 앱을 개발했습니다!  
이 앱은 사용자가 좋아하는 장르의 웹툰을 손쉽게 찾을 수 있도록, Gemini API 기반의 Vertax AI를 활용하여 장르 추천 AI 기능도 제공합니다.  
  
As a webtoon lover, you probably have favorite webtoons that you follow every day.  
Have you ever felt frustrated searching through multiple platforms?  
This app is here to solve that problem!  
It allows you to enjoy all your favorite webtoons in one place.  
Additionally, it features a webtoon recommendation system powered by the Gemini API!   

  #
  
* **요일별 웹툰 추가 기능** : 자신이 즐겨보는 웹툰을 요일별로 등록할 수 있습니다.
* **자동 URL 생성 및 웹뷰 연결** : 제목과 플랫폼을 설정하면, 해당 웹툰의 URL이 자동으로 생성되어 앱 내 웹뷰로 바로 연결됩니다.
* **간편한 웹뷰 내 탐색** : 웹뷰에서 ←, → 버튼을 눌러 이전 페이지와 다음 페이지로 쉽게 이동할 수 있습니다. 이 기능은 `SharedFlow`를 활용해 구현되었으며, `LaunchedEffect`로 버튼 동작을 감지하여 반응합니다.
* **AI 기반 웹툰 추천 기능** : Gemini API를 활용하여 사용자 취향에 맞는 웹툰을 추천합니다.
  <br></br>
* **Add Webtoons by Day** : Register your favorite webtoons by the day of the week.
* **Automatic URL Generation and Webview Integration** : Set the title and platform, and the app automatically generates the URL, seamlessly connecting you to the webtoon through the in-app webview.
* **Easy Webview Navigation** : Navigate easily with ← and → buttons to move to the previous or next page in the webview. This feature is implemented using `SharedFlow` and responds to button actions detected through `LaunchedEffect`.
* **AI-based Webtoon Recommendation** : Get personalized webtoon recommendations powered by the Gemini API.

## How To Use
### Add webtoons
![웹툰 추가](https://github.com/user-attachments/assets/b99d7c6c-863c-45bc-80d4-639f9f0f36d0)

### enjoy by WebView!
![explore webtoon](https://github.com/user-attachments/assets/de80a7d8-56f0-4773-a490-1821e33924db)

### get recommendation by Gemini!
![chat with gemini](https://github.com/user-attachments/assets/119ad00c-f0d9-4287-acd2-66ea1b63ce21)

### Login with google
![구글 로그인](https://github.com/user-attachments/assets/fb2283c8-12ee-44da-9748-59c9e5d6a989)

