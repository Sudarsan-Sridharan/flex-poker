const webpack = require('webpack');

module.exports = {
  entry: {
    app: './src/main/webapp/resources/index.js',
    vendor: [
      'lodash',
      'react',
      'react-bootstrap',
      'react-dom',
      'react-router',
      'sockjs-client',
      'webstomp-client'
    ]
  },

  output: {
    filename: 'src/main/webapp/resources/bundle.js',
    publicPath: ''
  },

  plugins: [
    new webpack.optimize.CommonsChunkPlugin('vendor', 'src/main/webapp/resources/vendor.bundle.js'),
    new webpack.DefinePlugin({"process.env": {NODE_ENV: JSON.stringify("production")}})
  ],

  module: {
    loaders: [
      { test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader?presets[]=es2015&presets[]=react' }
    ]
  }
}
