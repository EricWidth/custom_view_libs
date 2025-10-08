# Label 标签控件

一个高度自定义的 Android Label 标签控件，支持丰富的样式设置和动态属性修改。

## 特性

- ✅ 支持背景色、渐变背景设置
- ✅ 自定义边框颜色和宽度
- ✅ 文字样式定制（颜色、大小、间距）
- ✅ 图标显示控制
- ✅ 圆角设置
- ✅ 透明度调整
- ✅ 灵活的内边距和外边距设置
- ✅ 支持静态XML设置和代码动态修改

## 效果展示

当设置 `icon` 显示为 `false` 时，显示纯文字标签效果。

## 引入方式

### 通过 JitPack 引入

在项目的 `build.gradle` 中添加：

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

在模块的 `build.gradle` 中添加依赖：

```gradle
dependencies {
    	        implementation("com.github.EricWidth:custom_view_libs:release-v1.0.2")
}
```

## 使用方法

### XML 布局中使用

```xml
<com.yourpackage.LabelView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:labelText="示例标签"
    app:labelTextColor="#333333"
    app:labelTextSize="14sp"
    app:labelIconSrc="@drawable/ic_icon"
    app:labelIconVisible="true"
    app:labelIconSize="24dp"
    app:labelBackgroundColor="#FFFFFF"
    app:labelCornerRadius="8dp"
    app:labelBorderColor="#E0E0E0"
    app:labelBorderWidth="1dp"
    app:labelAlpha="1.0"
    app:labelPadding="8dp" />
```

### 代码中动态设置

```java
LabelView labelView = findViewById(R.id.label_view);

// 文本设置
labelView.setLabelText("动态文本");

// 图标设置
labelView.setIconVisible(true);
labelView.setIconSize(24);

// 背景设置
labelView.setBackgroundColor(Color.WHITE);
labelView.setCornerRadiusPx(16);
labelView.setBackgroundAlpha(0.8f);

// 渐变背景
labelView.setBackgroundGradient(startColor, endColor);

// 边框设置
labelView.setBorderWidthPx(2);
labelView.setBorderColor(Color.GRAY);

// 透明度
labelView.setAlphaValue(0.9f);

// 内边距
labelView.setPadding(10, 8, 10, 8);

// 文字外边距
labelView.setTextMarginPx(5, 0, 5, 0);
```

## 自定义属性

### 文本属性
- `labelText` - 标签文字
- `labelTextColor` - 文字颜色
- `labelTextSize` - 文字大小

### 图标属性
- `labelIconSrc` - 图标资源
- `labelIconVisible` - 图标是否可见
- `labelIconSize` - 图标大小

### 背景属性
- `labelBackgroundColor` - 背景颜色
- `labelBackgroundStartColor` - 渐变背景起始颜色
- `labelBackgroundEndColor` - 渐变背景结束颜色
- `labelCornerRadius` - 圆角半径
- `labelBackgroundAlpha` - 背景透明度

### 边框属性
- `labelBorderColor` - 边框颜色
- `labelBorderWidth` - 边框宽度

### 其他属性
- `labelAlpha` - 整体透明度

### 内边距属性
- `labelPadding` - 统一内边距
- `labelPaddingStart` - 起始内边距
- `labelPaddingEnd` - 结束内边距
- `labelPaddingTop` - 顶部内边距
- `labelPaddingBottom` - 底部内边距

### 文字外边距属性
- `labelTextMargin` - 统一文字外边距
- `labelTextMarginStart` - 起始文字外边距
- `labelTextMarginEnd` - 结束文字外边距
- `labelTextMarginTop` - 顶部文字外边距
- `labelTextMarginBottom` - 底部文字外边距

## 主要方法

| 方法名 | 描述 |
|--------|------|
| `setIconVisible()` | 设置图标可见性 |
| `setIconSize()` | 设置图标大小 |
| `setBackgroundColor()` | 设置背景颜色 |
| `setBackgroundAlpha()` | 设置背景透明度 |
| `setCornerRadiusPx()` | 设置圆角半径 |
| `setBackgroundGradient()` | 设置渐变背景 |
| `setAlphaValue()` | 设置整体透明度 |
| `setPadding()` | 设置内边距 |
| `setBorderWidthPx()` | 设置边框宽度 |
| `setBorderColor()` | 设置边框颜色 |
| `setTextMarginPx()` | 设置文字外边距 |

## 注意事项

**重要：** 在使用时，请参考 `MainActivity` 中的调用方式，否则可能无法正常显示效果。

## 更新日志

### v1.0.0
- 初始版本发布
- 支持基本标签功能
- 提供丰富的自定义属性

## 许可证

```
Copyright 2024 Your Name

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
