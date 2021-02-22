const path = require('path');

module.exports = {
    // 开发环境用于错误定位
    mode: 'development',
    // entry: './src/index.js',
    entry: ['./src/init.js', './src/component/detail.js', './src/component/info.js',
        './src/control/login.js', './src/control/register.js', './src/control/repair.js'],
    // 错误代码映射，追踪用
    devtool: 'inline-source-map',
    // 配置实时开发环境
    devServer: {
        contentBase: './dist',
    },
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'dist'),
    },
};