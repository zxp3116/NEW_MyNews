# NEW_MyNews
專案介紹：本App是新聞類相關的App，使用此App可了解時下的相關新聞，也可關鍵字進行新聞搜尋

1.不只可查看當日的焦點新聞、更可了解到國際上的發生的大小事及新聞，且實時更新

2.有第一手限免遊戲的動態消息，十分方便，同樣實時更新

3.有時下熱門的關鍵詞搜索，搜尋結果分為網頁文字(類似於網路文章的新聞)及影音類(如同將使用者的關鍵詞搜索進行YouTube的搜尋)

4.搜索過哪些的關鍵詞會進行本地儲存形成「關鍵詞搜尋紀錄」，讓使用者了解到自己曾經搜尋過哪些關鍵詞

原理：使用Jsoup框架進行資料的爬蟲，並將回傳的XML格式資料使用Thread多執行緒與Handler來處理，使其資料加載至ListView，之後利用Handler來更新UI介面

使用到的第三方類庫：jsoup、legacy-support、pierfrancescosoffritti.androidyoutubeplayer、glide、volley、lifecycle-extensions、navigation-fragment、ExpandableLayout、tagflowlayout、gson
