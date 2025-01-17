// import { ComboOption } from "@/shared/components/ComboBox";
// import {
//   initialConductCategory,
//   InitialMaterial,
// } from "@/shared/store/sharedStore";

// export const filteredCategoryList = (
//   initialConductCategoryList: initialConductCategory[],
//   company: ComboOption
// ) => {
//   const filterItem = initialConductCategoryList?.filter(
//     (e) => e.conductCompanyId === company?.conductCompanyId
//   );
//   if (filterItem && filterItem.length > 0) {
//     const result = [
//       { label: "전체", value: filterItem?.map((e) => e.value).join(",") },
//       ...filterItem,
//     ];
//     return result;
//   } else {
//     return initialConductCategoryList;
//   }
// };

// export const filteredMaterialList = (
//   initialMaterials: InitialMaterial[],
//   category: ComboOption
// ) => {
//   const filterItem = initialMaterials.filter((e) => {
//     if (typeof category?.value === "string" && e?.categoryId) {
//       return category.value.includes(e?.categoryId);
//     }
//   });

//   if (filterItem && filterItem.length > 0) {
//     const result = [
//       { label: "전체", value: filterItem?.map((e) => e.value).join(",") },
//       ...filterItem,
//     ];
//     return result;
//   } else {
//     return initialMaterials;
//   }
// };
