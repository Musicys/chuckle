<div data-v-8ab282ae="" data-v-4d353b5b="" class="markdown-preview-wrapper" _mstvisible="2"><div data-v-8ab282ae="" id="md-editor-v-2-0" class="md-editor md-editor-previewOnly" style="--md-bk-color: var(--datail-back-color); --md-color: var(--bk-font-color); --md-border-color: var(--cart-border-color); --md-color-primary: var(--bk-draw-back-color); --md-code-bk-color: var(--cart-back-color); --md-code-color: var(--bk-font-color); --md-blockquote-bk-color: var(--cart-back-color); --md-blockquote-border-color: var(--bk-draw-back-color); --md-border-radius: 8px; --md-font-size: 16px; --md-line-height: 2.2;" _mstvisible="3"><div id="md-editor-v-2-0-preview" class="md-editor-preview default-theme md-editor-scrn" _mstvisible="4"><h1 data-line="0" id="Flutter 核心技术速查手册" _msttexthash="48261226" _msthash="357" _mstvisible="5">Flutter 核心技术速查手册</h1>
<h2 data-line="2" id="1. 核心概念：万物皆 Widget" _msttexthash="40863004" _msthash="358" _mstvisible="5">1. 核心概念：万物皆 Widget</h2>
<p data-line="4" _msttexthash="796487939" _msthash="359" _mstvisible="5">Flutter 的 UI 构建基于 <strong>Widget（组件）</strong> 体系。在 Flutter 中，一切皆 Widget，无论是文本、按钮、布局容器，还是整个应用本身。</p>
<h3 data-line="6" id="1.1 Widget 的分类" _msttexthash="18523466" _msthash="360" _mstvisible="5">1.1 Widget 的分类</h3>
<ul data-line="8" _mstvisible="5">
<li data-line="8" _mstvisible="6"><font _mstmutation="1" _msttexthash="865698366" _msthash="361" _mstvisible="7"><strong _mstmutation="1">StatelessWidget（无状态组件）</strong>：UI 仅由配置决定，一旦构建完成，UI 不会因内部数据变化而更新。适用于纯展示型组件，如 、。</font><code _mstvisible="7">Text</code><code _mstvisible="7">Icon</code></li>
<li data-line="9" _mstvisible="6"><font _mstmutation="1" _msttexthash="735620938" _msthash="362" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">StatefulWidget（有状态组件）</strong>：持有 对象，UI 可以随状态变化而动态更新。适用于需要交互或数据变化的场景，如 、。</font><code _mstvisible="7">State</code><code _mstvisible="7">TextField</code><code _mstvisible="7">Checkbox</code></li>
</ul>
<h3 data-line="11" id="1.2 生命周期" _msttexthash="14838109" _msthash="363" _mstvisible="5">1.2 生命周期</h3>
<p data-line="13" _mstvisible="5"><font _mstmutation="1" _msttexthash="84131190" _msthash="364" _mstvisible="6">对于 ，理解生命周期至关重要：</font><code _mstvisible="6">StatefulWidget</code></p>
<ul data-line="15" _mstvisible="5">
<li data-line="15" _mstvisible="6"><code _mstvisible="7">initState()</code><font _mstmutation="1" _msttexthash="217971559" _msthash="365" _mstvisible="7">：组件初始化时调用，<strong _mstmutation="1">必须</strong>先调用 。常用于初始化数据、订阅事件。</font><code _mstvisible="7">super.initState()</code></li>
<li data-line="16" _mstvisible="6"><code _mstvisible="7">dispose()</code><font _mstmutation="1" _msttexthash="433414514" _msthash="366" _mstvisible="7">：组件销毁时调用，<strong _mstmutation="1">必须</strong>调用 。用于清理资源，如取消定时器、关闭流订阅，防止内存泄漏。</font><code _mstvisible="7">super.dispose()</code></li>
</ul>
<h3 data-line="18" id="1.3 热重载 (Hot Reload)" _msttexthash="60484021" _msthash="367" _mstvisible="5">1.3 热重载（热重载）</h3>
<p data-line="20" _mstvisible="5"><font _mstmutation="1" _msttexthash="1265538677" _msthash="368" _mstvisible="6">Flutter 的杀手级特性。修改代码后，无需重启应用即可在毫秒级看到 UI 变化，且<strong _mstmutation="1" _istranslated="1">保留当前应用状态</strong>（如输入框内容、滚动位置）。若修改了 函数或全局变量，则需使用 <strong _mstmutation="1" _istranslated="1">Hot Restart</strong>。</font><code _mstvisible="6">main()</code></p>
<hr data-line="22" _mstvisible="5">
<h2 data-line="24" id="2. 布局系统：约束与弹性" _msttexthash="52938548" _msthash="369" _mstvisible="5">2. 布局系统：约束与弹性</h2>
<p data-line="26" _msttexthash="1192838465" _msthash="370" _mstvisible="5">Flutter 的布局模型基于<strong>约束传递</strong>：父组件向子组件传递约束（最小/最大宽高），子组件在约束范围内决定自身大小，父组件再根据子组件的大小决定自身位置。</p>
<h3 data-line="28" id="2.1 基础布局组件" _msttexthash="24938589" _msthash="371" _mstvisible="5">2.1 基础布局组件</h3>
<table data-line="30" _mstvisible="5">
<thead data-line="30" _mstvisible="6">
<tr data-line="30" _mstvisible="7">
<th style="text-align:left" _msttexthash="5055388" _msthash="372" _mstvisible="8">组件</th>
<th style="text-align:left" _msttexthash="4967924" _msthash="373" _mstvisible="8">作用</th>
<th style="text-align:left" _msttexthash="11835733" _msthash="374" _mstvisible="8">关键属性</th>
</tr>
</thead>
<tbody data-line="32" _mstvisible="6">
<tr data-line="32" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">Container</code></td>
<td style="text-align:left" _msttexthash="11739858" _msthash="375" _mstvisible="8">容器/盒子</td>
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">padding</code><font _mstmutation="1" _msttexthash="19448" _msthash="376" _mstvisible="9">, , , ,</font><code _mstvisible="9">margin</code><code _mstvisible="9">color</code><code _mstvisible="9">decoration</code><code _mstvisible="9">child</code></td>
</tr>
<tr data-line="33" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">Row</code></td>
<td style="text-align:left" _msttexthash="10921235" _msthash="377" _mstvisible="8">水平布局</td>
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">mainAxisAlignment</code><font _mstmutation="1" _msttexthash="20876752" _msthash="378" _mstvisible="9">(主轴),  (交叉轴)</font><code _mstvisible="9">crossAxisAlignment</code></td>
</tr>
<tr data-line="34" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">Column</code></td>
<td style="text-align:left" _msttexthash="11091509" _msthash="379" _mstvisible="8">垂直布局</td>
<td style="text-align:left" _msttexthash="26514358" _msthash="380" _mstvisible="8">同上，方向相反</td>
</tr>
<tr data-line="35" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">Stack</code></td>
<td style="text-align:left" _msttexthash="10268245" _msthash="381" _mstvisible="8">层叠布局</td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="50710842" _msthash="382" _mstvisible="9">子组件可重叠，配合  定位</font><code _mstvisible="9">Positioned</code></td>
</tr>
<tr data-line="36" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><code _mstvisible="9">Center</code></td>
<td style="text-align:left" _msttexthash="4230863" _msthash="383" _mstvisible="8">居中</td>
<td style="text-align:left" _msttexthash="59068009" _msthash="384" _mstvisible="8">将子组件在水平和垂直方向居中</td>
</tr>
</tbody>
</table>
<h3 data-line="38" id="2.2 弹性布局 (Flex)" _msttexthash="42545009" _msthash="385" _mstvisible="5">2.2 弹性布局 （Flex）</h3>
<p data-line="40" _mstvisible="5"><font _mstmutation="1" _msttexthash="147369183" _msthash="386" _mstvisible="6">当需要在  或  中分配剩余空间时，使用弹性组件：</font><code _mstvisible="6">Row</code><code _mstvisible="6">Column</code></p>
<ul data-line="42" _mstvisible="5">
<li data-line="42" _mstvisible="6"><font _mstmutation="1" _msttexthash="268839883" _msthash="387" _mstvisible="7"><strong _mstmutation="1"><code>Expanded</code></strong>：<strong _mstmutation="1">强制</strong>子组件填满剩余空间。若多个  存在，按  系数比例分配。</font><code _mstvisible="7">Expanded</code><code _mstvisible="7">flex</code></li>
</ul>

        <div data-line="44" class="md-editor-code" _mstvisible="5">
          <div class="md-editor-code-head" _mstvisible="6">
            <div class="md-editor-code-flag" _mstvisible="7"><span _mstvisible="8"></span><span _mstvisible="8"></span><span _mstvisible="8"></span></div>
            <div class="md-editor-code-action" _mstvisible="7">
              <span class="md-editor-code-lang" _msttexthash="7537114" _msthash="535" _mstvisible="8">飞镖</span>
              <span class="md-editor-copy-button" data-tips="复制代码" data-processed="true" _msttexthash="10619856" _msthash="536" _mstvisible="8">复制代码</span>
              
              
            </div>
          </div>
          <pre _mstvisible="6"><code class="language-dart" language="dart" _mstvisible="7"><span class="md-editor-code-block" _mstvisible="8"><span class="hljs-selector-tag" _mstvisible="9">Row</span>(<span class="hljs-attribute" _mstvisible="9">children</span>: [
  <span class="hljs-built_in" _mstvisible="9">Expanded</span>(<span class="hljs-attribute" _mstvisible="9">flex</span>: <span class="hljs-number" _mstvisible="9">1</span>, <span class="hljs-attribute" _mstvisible="9">child</span>: <span class="hljs-built_in" _mstvisible="9">Container</span>(<span class="hljs-attribute" _mstvisible="9">color</span>: Colors.red)),
  <span class="hljs-built_in" _mstvisible="9">Expanded</span>(<span class="hljs-attribute" _mstvisible="9">flex</span>: <span class="hljs-number" _mstvisible="9">2</span>, <span class="hljs-attribute" _mstvisible="9">child</span>: <span class="hljs-built_in" _mstvisible="9">Container</span>(<span class="hljs-attribute" _mstvisible="9">color</span>: Colors.blue)),
])</span><span rn-wrapper="" aria-hidden="true" _mstvisible="8"><span _mstvisible="9"></span><span _mstvisible="9"></span><span _mstvisible="9"></span><span _mstvisible="9"></span></span></code></pre>

        </div>
      <ul data-line="51" _mstvisible="5">
<li data-line="51" _msttexthash="361093824" _msthash="533" _mstvisible="6"><strong><code>Flexible</code></strong>：<strong>允许</strong>子组件填满剩余空间，但不强制。若子组件内容较小，它不会拉伸。</li>
<li data-line="52" _mstvisible="6"><font _mstmutation="1" _msttexthash="142296596" _msthash="534" _mstvisible="7"><strong _mstmutation="1"><code>Spacer</code></strong>：等价于 ，用于在组件间创建弹性空白。</font><code _mstvisible="7">Expanded(flex: 1)</code></li>
</ul>
<blockquote data-line="54" _mstvisible="5">
<p data-line="54" _mstvisible="6"><font _mstmutation="1" _msttexthash="456338272" _msthash="532" _mstvisible="7"><strong _mstmutation="1">⚠️ 常见错误</strong>：在  中直接放一个无限宽度的子组件（如  或无约束的 ），会导致 。务必使用  或  包裹。</font><code _mstvisible="7">Row</code><code _mstvisible="7">ListView</code><code _mstvisible="7">Text</code><code _mstvisible="7">RenderFlex overflowed</code><code _mstvisible="7">Expanded</code><code _mstvisible="7">Flexible</code></p>
</blockquote>
<hr data-line="56" _mstvisible="5">
<h2 data-line="58" id="3. 状态管理：从局部到全局" _msttexthash="58188598" _msthash="531" _mstvisible="5">3. 状态管理：从局部到全局</h2>
<p data-line="60" _msttexthash="283625160" _msthash="530" _mstvisible="5">状态管理是 Flutter 开发的核心挑战，根据应用规模选择合适的方案：</p>
<h3 data-line="62" id="3.1 局部状态：setState" _mstvisible="5"><font _mstmutation="1" _msttexthash="29010267" _msthash="529" _mstvisible="6">3.1 局部状态：</font><code _mstvisible="6">setState</code></h3>
<p data-line="64" _msttexthash="154711596" _msthash="528" _mstvisible="5">适用于单组件内部的状态变更（如计数器、开关）。</p>

        <div data-line="66" class="md-editor-code" _mstvisible="5">
          <div class="md-editor-code-head" _mstvisible="6">
            <div class="md-editor-code-flag" _mstvisible="7"><span _mstvisible="8"></span><span _mstvisible="8"></span><span _mstvisible="8"></span></div>
            <div class="md-editor-code-action" _mstvisible="7">
              <span class="md-editor-code-lang" _msttexthash="7537114" _msthash="526" _mstvisible="8">飞镖</span>
              <span class="md-editor-copy-button" data-tips="复制代码" data-processed="true" _msttexthash="10619856" _msthash="527" _mstvisible="8">复制代码</span>
              
              
            </div>
          </div>
          <pre _mstvisible="6"><code class="language-dart" language="dart" _mstvisible="7"><span class="md-editor-code-block" _mstvisible="8"><span class="hljs-built_in" _mstvisible="9">setState</span>(() {
  _counter++;
});</span><span rn-wrapper="" aria-hidden="true" _mstvisible="8"><span _mstvisible="9"></span><span _mstvisible="9"></span><span _mstvisible="9"></span></span></code></pre>

        </div>
      <ul data-line="72" _mstvisible="5">
<li data-line="72" _msttexthash="75743460" _msthash="524" _mstvisible="6"><strong>优点</strong>：简单直接，无额外依赖。</li>
<li data-line="73" _msttexthash="120606239" _msthash="525" _mstvisible="6"><strong>缺点</strong>：状态难以跨组件共享，逻辑与 UI 耦合。</li>
</ul>
<h3 data-line="75" id="3.2 跨组件状态：Provider" _mstvisible="5"><font _mstmutation="1" _msttexthash="34702473" _msthash="523" _mstvisible="6">3.2 跨组件状态：</font><code _mstvisible="6">Provider</code></h3>
<p data-line="77" _mstvisible="5"><font _mstmutation="1" _msttexthash="88969803" _msthash="522" _mstvisible="6">官方推荐的轻量级方案，基于  封装。</font><code _mstvisible="6">InheritedWidget</code></p>
<ul data-line="79" _mstvisible="5">
<li data-line="79" _mstvisible="6"><font _mstmutation="1" _msttexthash="14547221" _msthash="520" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">核心</strong>： + + 。</font><code _mstvisible="7">ChangeNotifier</code><code _mstvisible="7">ChangeNotifierProvider</code><code _mstvisible="7">Consumer</code></li>
<li data-line="80" _msttexthash="158796183" _msthash="521" _mstvisible="6"><strong>适用</strong>：中小型应用，主题切换、用户信息、购物车等。</li>
</ul>
<h3 data-line="82" id="3.3 复杂架构：Riverpod / Bloc" _mstvisible="5"><font _mstmutation="1" _msttexthash="27265056" _msthash="519" _mstvisible="6">3.3 复杂架构：</font><code _mstvisible="6">Riverpod</code> / <code _mstvisible="6">Bloc</code></h3>
<ul data-line="84" _mstvisible="5">
<li data-line="84" _mstvisible="6"><font _mstmutation="1" _msttexthash="301567643" _msthash="517" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">Riverpod</strong>:P rovider 的进化版，编译时安全，支持异步状态，无需 。</font><code _mstvisible="7">BuildContext</code></li>
<li data-line="85" _msttexthash="366864862" _msthash="518" _mstvisible="6"><strong>Bloc</strong>：基于事件驱动（Event -&gt; State），逻辑与 UI 彻底分离，适合大型团队协作。</li>
</ul>
<hr data-line="87" _mstvisible="5">
<h2 data-line="89" id="4. 性能优化与最佳实践" _msttexthash="38447617" _msthash="516" _mstvisible="5">4. 性能优化与最佳实践</h2>
<h3 data-line="91" id="4.1 构建优化" _msttexthash="13690469" _msthash="515" _mstvisible="5">4.1 构建优化</h3>
<ul data-line="93" _mstvisible="5">
<li data-line="93" _mstvisible="6"><font _mstmutation="1" _msttexthash="574778191" _msthash="512" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">避免在 <code _istranslated="1">build（）</code> 中创建对象</strong>： 方法可能每秒调用 60 次。将 构造函数用于不变的 Widget，使用 变量缓存复杂计算。</font><code _mstvisible="7">build</code><code _mstvisible="7">const</code><code _mstvisible="7">final</code></li>
<li data-line="94" _mstvisible="6"><font _mstmutation="1" _msttexthash="402123423" _msthash="513" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">拆分 Widget</strong>：将大型方法拆分为多个小 Widget，利用 Flutter 的 Diff 算法减少不必要的重绘范围。</font><code _mstvisible="7">build</code></li>
<li data-line="95" _msttexthash="514993609" _msthash="514" _mstvisible="6"><strong>使用 <code>RepaintBoundary</code></strong>：隔离频繁变化的区域（如动画、视频），防止重绘扩散到整个页面。</li>
</ul>
<h3 data-line="97" id="4.2 列表优化" _msttexthash="14494194" _msthash="511" _mstvisible="5">4.2 列表优化</h3>
<ul data-line="99" _mstvisible="5">
<li data-line="99" _mstvisible="6"><font _mstmutation="1" _msttexthash="354094936" _msthash="509" _mstvisible="7"><strong _mstmutation="1">必须使用 <code>ListView.builder</code></strong>：禁止在长列表中使用 ，后者会一次性构建所有子项。</font><code _mstvisible="7">ListView(children: [...])</code></li>
<li data-line="100" _mstvisible="6"><font _mstmutation="1" _msttexthash="519037974" _msthash="510" _mstvisible="7"><strong _mstmutation="1">Key 的重要性</strong>：在动态列表中，为每个项提供唯一的 （如 ），帮助框架正确复用 State，避免状态错乱。</font><code _mstvisible="7">Key</code><code _mstvisible="7">ValueKey(item.id)</code></li>
</ul>
<h3 data-line="102" id="4.3 调试工具" _msttexthash="17068311" _msthash="508" _mstvisible="5">4.3 调试工具</h3>
<ul data-line="104" _mstvisible="5">
<li data-line="104" _mstvisible="6"><font _mstmutation="1" _msttexthash="33598591" _msthash="504" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">Flutter开发工具</strong>：</font><ul data-line="105" _mstvisible="7">
<li data-line="105" _msttexthash="276225079" _msthash="505" _mstvisible="8"><strong>Flutter Inspector</strong>：可视化查看 Widget 树和 Render 树，快速定位布局溢出。</li>
<li data-line="106" _msttexthash="379675335" _msthash="506" _mstvisible="8"><strong _istranslated="1">Performance View</strong>：分析帧率，识别掉帧原因（UI 线程阻塞 vs 栅格线程阻塞）。</li>
<li data-line="107" _msttexthash="163324928" _msthash="507" _mstvisible="8"><strong>Memory View</strong>：监控内存泄漏，查看对象分配情况。</li>
</ul>
</li>
</ul>
<hr data-line="109" _mstvisible="5">
<h2 data-line="111" id="5. 跨平台与生态" _msttexthash="23133370" _msthash="503" _mstvisible="5">5. 跨平台与生态</h2>
<h3 data-line="113" id="5.1 多端支持" _msttexthash="15805166" _msthash="502" _mstvisible="5">5.1 多端支持</h3>
<p data-line="115" _msttexthash="557772176" _msthash="501" _mstvisible="5">Flutter 支持 <strong _istranslated="1">Android， iOS， Web， Windows， macOS， Linux</strong> 六大平台，一套代码库，90% 以上逻辑复用。</p>
<ul data-line="117" _mstvisible="5">
<li data-line="117" _msttexthash="568117407" _msthash="499" _mstvisible="6"><strong>Web</strong>：支持 CanvasKit（高性能，包体大）和 HTML Renderer（兼容性好，包体小），未来将全面支持 WebAssembly。</li>
<li data-line="118" _msttexthash="147178629" _msthash="500" _mstvisible="6"><strong>桌面</strong>：原生渲染，支持窗口管理、文件系统访问。</li>
</ul>
<h3 data-line="120" id="5.2 平台交互" _msttexthash="12771369" _msthash="498" _mstvisible="5">5.2 平台交互</h3>
<ul data-line="122" _mstvisible="5">
<li data-line="122" _mstvisible="6"><font _mstmutation="1" _msttexthash="497312751" _msthash="496" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">Platform Channel</strong>：通过 或 与原生代码（Kotlin/Swift）通信，调用相机、蓝牙、传感器等原生 API。</font><code _mstvisible="7">MethodChannel</code><code _mstvisible="7">EventChannel</code></li>
<li data-line="123" _mstvisible="6"><font _mstmutation="1" _msttexthash="366365701" _msthash="497" _mstvisible="7"><strong _mstmutation="1" _istranslated="1">Platform Views</strong>：在 Flutter 中嵌入原生视图（如 / ），用于地图、WebView 等复杂场景。</font><code _mstvisible="7">AndroidView</code><code _mstvisible="7">UiKitView</code></li>
</ul>
<h3 data-line="125" id="5.3 环境检查" _msttexthash="15783950" _msthash="495" _mstvisible="5">5.3 环境检查</h3>
<p data-line="127" _msttexthash="36691278" _msthash="494" _mstvisible="5">开发前务必运行：</p>

        <div data-line="129" class="md-editor-code" _mstvisible="5">
          <div class="md-editor-code-head" _mstvisible="6">
            <div class="md-editor-code-flag" _mstvisible="7"><span _mstvisible="8"></span><span _mstvisible="8"></span><span _mstvisible="8"></span></div>
            <div class="md-editor-code-action" _mstvisible="7">
              <span class="md-editor-code-lang" _msttexthash="2799888" _msthash="492" _mstvisible="8">砰</span>
              <span class="md-editor-copy-button" data-tips="复制代码" data-processed="true" _msttexthash="10619856" _msthash="493" _mstvisible="8">复制代码</span>
              
              
            </div>
          </div>
          <pre _mstvisible="6"><code class="language-bash" language="bash" _mstvisible="7"><span class="md-editor-code-block" _mstvisible="8">flutter doctor</span><span rn-wrapper="" aria-hidden="true" _mstvisible="8"><span _mstvisible="9"></span></span></code></pre>

        </div>
      <p data-line="133" _mstvisible="5"><font _mstmutation="1" _msttexthash="403874666" _msthash="491" _mstvisible="6">确保 Android SDK、Xcode、Chrome、VS Code/Android Studio 等依赖均显示 。若遇 License 问题，执行 。</font><code _mstvisible="6">√</code><code _mstvisible="6">flutter doctor --android-licenses</code></p>
<hr data-line="135" _mstvisible="5">
<h2 data-line="137" id="6. 常见陷阱速查" _msttexthash="29998358" _msthash="490" _mstvisible="5">6. 常见陷阱速查</h2>
<table data-line="139" _mstvisible="5">
<thead data-line="139" _mstvisible="6">
<tr data-line="139" _mstvisible="7">
<th style="text-align:left" _msttexthash="7555418" _msthash="472" _mstvisible="8">问题</th>
<th style="text-align:left" _msttexthash="4260997" _msthash="473" _mstvisible="8">原因</th>
<th style="text-align:left" _msttexthash="11904646" _msthash="474" _mstvisible="8">解决方案</th>
</tr>
</thead>
<tbody data-line="141" _mstvisible="6">
<tr data-line="141" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><strong _msttexthash="14412788" _msthash="475" _mstvisible="9">Widget 未更新</strong></td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="34242039" _msthash="476" _mstvisible="9">修改了状态但未调用</font><code _mstvisible="9">setState</code></td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="37693461" _msthash="477" _mstvisible="9">确保在  回调中修改状态</font><code _mstvisible="9">setState</code></td>
</tr>
<tr data-line="142" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><strong _msttexthash="37172668" _msthash="478" _mstvisible="9">布局溢出 （Overflow）</strong></td>
<td style="text-align:left" _msttexthash="41129088" _msthash="479" _mstvisible="8">子组件超出父组件约束</td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="9673222" _msthash="480" _mstvisible="9">使用 、 或</font><code _mstvisible="9">Expanded</code><code _mstvisible="9">Flexible</code><code _mstvisible="9">SingleChildScrollView</code></td>
</tr>
<tr data-line="143" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><strong _msttexthash="20765680" _msthash="481" _mstvisible="9">列表状态错乱</strong></td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="23499957" _msthash="482" _mstvisible="9">动态列表未使用</font><code _mstvisible="9">Key</code></td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="27813292" _msthash="483" _mstvisible="9">为列表项添加唯一</font><code _mstvisible="9">Key</code></td>
</tr>
<tr data-line="144" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><strong _msttexthash="11284793" _msthash="484" _mstvisible="9">内存泄漏</strong></td>
<td style="text-align:left" _msttexthash="31089279" _msthash="485" _mstvisible="8">未取消订阅/定时器</td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="20855185" _msthash="486" _mstvisible="9">在  中清理资源</font><code _mstvisible="9">dispose()</code></td>
</tr>
<tr data-line="145" _mstvisible="7">
<td style="text-align:left" _mstvisible="8"><strong _msttexthash="17486274" _msthash="487" _mstvisible="9">热重载失效</strong></td>
<td style="text-align:left" _mstvisible="8"><font _mstmutation="1" _msttexthash="27258374" _msthash="488" _mstvisible="9">修改了  或全局变量</font><code _mstvisible="9">main()</code></td>
<td style="text-align:left" _msttexthash="45298565" _msthash="489" _mstvisible="8">使用 <strong _istranslated="1">热重启</strong>（Shift+F10）</td>
</tr>
</tbody>
</table>
<hr data-line="147" _mstvisible="5">
<blockquote data-line="149" _mstvisible="5">
<p data-line="149" _msttexthash="36349261" _msthash="467" _mstvisible="6"><strong>💡 学习建议</strong>：</p>
<ol data-line="151" _mstvisible="6">
<li data-line="151" _msttexthash="200029817" _msthash="468" _mstvisible="7"><strong>先跑通</strong>：不要死磕 Dart 语法，先跟着官方 Codelabs 跑通一个完整 App。</li>
<li data-line="152" _mstvisible="7"><font _mstmutation="1" _msttexthash="255846058" _msthash="469" _mstvisible="8"><strong _mstmutation="1">看源码</strong>：遇到布局问题，直接看  或  的源码注释，官方文档极其详尽。</font><code _mstvisible="8">Container</code><code _mstvisible="8">Row</code></li>
<li data-line="153" _msttexthash="291682365" _msthash="470" _mstvisible="7"><strong>用工具</strong>：养成打开 <strong>Flutter Inspector</strong> 的习惯，布局问题 90% 是约束理解错误。</li>
<li data-line="154" _mstvisible="7"><font _mstmutation="1" _msttexthash="271097996" _msthash="471" _mstvisible="8"><strong _mstmutation="1" _istranslated="1">关注版本</strong>：Flutter 迭代快，注意 API 的 标记，及时迁移（如 -&gt; ）。</font><code _mstvisible="8">@Deprecated</code><code _mstvisible="8">FlatButton</code><code _mstvisible="8">TextButton</code></li>
</ol>
</blockquote>
</div><!----></div></div>