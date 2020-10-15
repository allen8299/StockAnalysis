# StockAnalysis
Data: https://www.dropbox.com/s/wmq4he9819x93k8/stock2016-2018.rar?dl=0


2018年完成，範例為2016~2018年的股市資料

--UI--

![image](https://github.com/allen8299/StockAnalysis/blob/master/UI.JPG)

--單一邏輯--

![image](https://github.com/allen8299/StockAnalysis/blob/master/singleSearch.JPG)

--合併邏輯--

![image](https://github.com/allen8299/StockAnalysis/blob/master/combineSearch.JPG)

--Class介紹--

#Basic.java 基本資訊

getLegalBuyAndSell 法人買賣超

getPE 本益比

getPbr 股價淨值比

getYieldRate 殖利率

getShare 成交量計算

#Data.java 標的陣列

getStockArray 建立stock array

#Credit.java 有關信用交易

getFinancing 融資增減

getMargin 融券增減

#Display.java 顯示UI

#Technique.java 特殊選股法

getHigestShareAndPrice 近x日成交量最大且股價最新高

