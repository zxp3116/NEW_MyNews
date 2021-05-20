# NEW_MyNews
專案介紹：本App是新聞類相關的App，使用此App可了解時下的相關新聞，也可關鍵字進行新聞搜尋

1.不只可查看當日的焦點新聞、更可了解到國際上的發生的大小事及新聞，且實時更新

2.有第一手限免遊戲的動態消息，十分方便，同樣實時更新

3.有時下熱門的關鍵詞搜索，搜尋結果分為網頁文字(類似於網路文章的新聞)及影音類(如同將使用者的關鍵詞搜索進行YouTube的搜尋)

4.搜索過哪些的關鍵詞會進行本地儲存形成「關鍵詞搜尋紀錄」，讓使用者了解到自己曾經搜尋過哪些關鍵詞

原理：首先使用Fragment實現底部導航欄，並分成三頁為新聞頭條、新聞搜索、設置，
新聞頭條：使用ViewPager再分成三頁為頭條新聞、國際新聞、商業新聞，以達到可觀看不同種類的新聞，並可左右滑動。並使用Jsoup框架進行資料的爬蟲，將回傳的HTML格式資料以AsyncTask異步任務加載到自訂的ListVie上進行呈現，同時使用Handler達到動態更新UI介面。
新聞搜索：使用Jsoup框架進行資料的爬蟲，以達到在Google新聞的關鍵詞搜索，並將回傳的XML格式資料使用Thread多執行緒與Handler來處理，使其資料加載至ListView，之後利用Handler來更新UI介面，搜尋紀錄則使用SharedPreferences的方式來儲存使用者搜尋過哪些的關鍵詞。

使用到的第三方類庫：jsoup、legacy-support、pierfrancescosoffritti.androidyoutubeplayer、glide、volley、lifecycle-extensions、navigation-fragment、ExpandableLayout、tagflowlayout、gson


![Screenshot_20210520-080937](https://user-images.githubusercontent.com/71322458/118900308-351d6b80-b943-11eb-9e74-12eb3a75d35b.png)

![Screenshot_20210520-081042](https://user-images.githubusercontent.com/71322458/118900309-364e9880-b943-11eb-97a8-8f9db11a9bd8.png)

![Screenshot_20210520-081057](https://user-images.githubusercontent.com/71322458/118900312-377fc580-b943-11eb-8bbe-cec4ae90175c.png)

![Screenshot_20210520-081106](https://user-images.githubusercontent.com/71322458/118900315-38b0f280-b943-11eb-8416-a3e450a08d6d.png)
