// 菜单项类型
export interface MenuItem {
   id: string; // 唯一标识
   name: string; // 显示名称
   icon: string; // 图标名称（Element Plus Icons）
   handler: () => void; // 点击处理函数
   visible?: boolean; // 是否显示
   divider?: boolean; // 是否为分隔线
}

// 菜单分组类型
export interface MenuGroup {
   id: string;
   items: MenuItem[];
}

// 右键菜单状态
export interface RightMenuState {
   visible: boolean;
   x: number;
   y: number;
}
