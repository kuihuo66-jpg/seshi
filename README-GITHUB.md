# 10秒极限反应：手机云端编译说明

## 用手机生成 APK

1. 在 GitHub 新建一个仓库，例如 `ExtremeReaction10s`。
2. 把本项目内的所有文件上传到仓库根目录。注意 `.github/workflows/build-apk.yml` 也必须上传。
3. 上传后进入仓库顶部的 **Actions**。
4. 第一次如果看到工作流，打开 `Build Android APK`。
5. 点击 **Run workflow**（如果没有按钮，先把代码推送到 `main`，它会自动运行）。
6. 等待任务变成绿色 `Success`。
7. 点进这次运行，在页面底部找到 **Artifacts**。
8. 下载 `ExtremeReaction10s-debug-apk`，解压后得到 `app-debug.apk`。
9. 在 Android 手机上点击 APK 安装。若系统提示“允许安装未知来源应用”，按系统提示开启即可。

## 注意

- 这是 debug APK，适合自己安装和测试，不是用于 Google Play 发布的正式签名包。
- GitHub Actions 是云端编译，不需要在手机安装 Android Studio。
- 如果 Actions 报错，把红色错误日志截图发给我，我可以继续修改项目。
