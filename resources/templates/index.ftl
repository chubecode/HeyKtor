<html>

    <h2>Hello ${data.name}!</h2>

    Your age is ${data.age}

    <!-- TradingView Widget BEGIN -->
    <div class="tradingview-widget-container">
      <div id="tradingview_d644f"></div>
      <div class="tradingview-widget-copyright"><a href="https://vn.tradingview.com/symbols/NASDAQ-AAPL/" rel="noopener" target="_blank"><span class="blue-text">AAPL Biểu đồ</span> </a> bởi TradingView</div>
      <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
      <script type="text/javascript">
      new TradingView.widget(
      {
      "width": 980,
      "height": 610,
      "symbol": "NASDAQ:AAPL",
      "interval": "D",
      "timezone": "Etc/UTC",
      "theme": "Light",
      "style": "1",
      "locale": "vi_VN",
      "toolbar_bg": "#f1f3f6",
      "enable_publishing": false,
      "allow_symbol_change": true,
      "container_id": "tradingview_d644f"
    }
      );
      </script>
    </div>
    <!-- TradingView Widget END -->

</html>