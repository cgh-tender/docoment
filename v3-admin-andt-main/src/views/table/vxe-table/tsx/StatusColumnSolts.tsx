import { type VxeColumnPropTypes } from "vxe-table/types/column";

const solts: VxeColumnPropTypes.Slots = {
  default: ({ row, column }) => {
    const cellValue = row[column.field];
    const [type, value] = cellValue ? ["success", "启用"] : ["danger", "禁用"];
    // @ts-ignore
    return [<span class={`a-tag a-tag--${type} a-tag--plain`}>{value}</span>];
  }
};

export default solts;
