<script setup lang="ts">
import Language from "@/utils/cron/language"
import { computed, reactive, ref, watchEffect } from "vue"

defineOptions({
  name: "vue3Cron"
})

const props = defineProps({
  cronValue: {},
  i18n: {},
  maxHeight: String,
  language: {},
  sysRadio: String
})
const emit = defineEmits(["changeCron", "close"])

const sysRadio = ref(props.sysRadio)

const showSecond = ref()
const showYear = ref()

watchEffect(() => {
  const t = Number(sysRadio.value)
  showSecond.value = t !== 1
  showYear.value = t === 3
})

const state = reactive({
  language: props.language,
  second: {
    cronEvery: "1",
    incrementStart: 3,
    incrementIncrement: 5,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: []
  },
  minute: {
    cronEvery: "1",
    incrementStart: 3,
    incrementIncrement: 5,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: []
  },
  hour: {
    cronEvery: "1",
    incrementStart: 3,
    incrementIncrement: 5,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: []
  },
  day: {
    cronEvery: "1",
    incrementStart: 1,
    incrementIncrement: 1,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: [],
    cronLastSpecificDomDay: 1,
    cronDaysBeforeEomMinus: 0,
    cronDaysNearestWeekday: 0
  },
  week: {
    cronEvery: "1",
    incrementStart: 1,
    incrementIncrement: 1,
    specificSpecific: [],
    cronNthDayDay: 1,
    cronNthDayNth: 1
  },
  month: {
    cronEvery: "1",
    incrementStart: 3,
    incrementIncrement: 5,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: []
  },
  year: {
    cronEvery: "1",
    incrementStart: 2017,
    incrementIncrement: 1,
    rangeStart: 0,
    rangeEnd: 0,
    specificSpecific: []
  },
  output: {
    second: "",
    minute: "",
    hour: "",
    day: "",
    month: "",
    week: "",
    year: ""
  },
  text: computed(() => {
    return Language[state.language || "cn"]
  }),
  secondsText: computed(() => {
    let seconds = ""
    const cronEvery = state.second.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        seconds = "*"
        break
      case "2":
        seconds = state.second.incrementStart + "/" + state.second.incrementIncrement
        break
      case "3":
        state.second.specificSpecific.map((val: string) => {
          seconds += val + ","
        })
        seconds = seconds.slice(0, -1)
        break
      case "4":
        seconds = state.second.rangeStart + "-" + state.second.rangeEnd
        break
    }
    return seconds
  }),
  minutesText: computed(() => {
    let minutes = ""
    const cronEvery = state.minute.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        minutes = "*"
        break
      case "2":
        minutes = state.minute.incrementStart + "/" + state.minute.incrementIncrement
        break
      case "3":
        state.minute.specificSpecific.map((val: string) => {
          minutes += val + ","
        })
        minutes = minutes.slice(0, -1)
        break
      case "4":
        minutes = state.minute.rangeStart + "-" + state.minute.rangeEnd
        break
    }
    return minutes
  }),
  hoursText: computed(() => {
    let hours = ""
    const cronEvery = state.hour.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        hours = "*"
        break
      case "2":
        hours = state.hour.incrementStart + "/" + state.hour.incrementIncrement
        break
      case "3":
        state.hour.specificSpecific.map((val: string) => {
          hours += val + ","
        })
        hours = hours.slice(0, -1)
        break
      case "4":
        hours = state.hour.rangeStart + "-" + state.hour.rangeEnd
        break
    }
    return hours
  }),
  daysText: computed(() => {
    let days = ""
    const cronEvery = state.day.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        break
      case "2":
      case "4":
      case "11":
        days = "?"
        break
      case "3":
        days = state.day.incrementStart + "/" + state.day.incrementIncrement
        break
      case "5":
        state.day.specificSpecific.map((val: string) => {
          days += val + ","
        })
        days = days.slice(0, -1)
        break
      case "6":
        days = "L"
        break
      case "7":
        days = "LW"
        break
      case "8":
        days = state.day.cronLastSpecificDomDay + "L"
        break
      case "9":
        days = "L-" + state.day.cronDaysBeforeEomMinus
        break
      case "10":
        days = state.day.cronDaysNearestWeekday + "W"
        break
    }
    return days
  }),
  weeksText: computed(() => {
    let weeks = ""
    const cronEvery = state.day.cronEvery
    switch (cronEvery.toString()) {
      case "1":
      case "3":
      case "5":
        weeks = "?"
        break
      case "2":
        weeks = state.week.incrementStart + "/" + state.week.incrementIncrement
        break
      case "4":
        state.week.specificSpecific.map((val: string) => {
          weeks += val + ","
        })
        weeks = weeks.slice(0, -1)
        break
      case "6":
      case "7":
      case "8":
      case "9":
      case "10":
        weeks = "?"
        break
      case "11":
        weeks = state.week.cronNthDayDay + "#" + state.week.cronNthDayNth
        break
    }
    return weeks
  }),
  monthsText: computed(() => {
    let months = ""
    const cronEvery = state.month.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        months = "*"
        break
      case "2":
        months = state.month.incrementStart + "/" + state.month.incrementIncrement
        break
      case "3":
        state.month.specificSpecific.map((val: string) => {
          months += val + ","
        })
        months = months.slice(0, -1)
        break
      case "4":
        months = state.month.rangeStart + "-" + state.month.rangeEnd
        break
    }
    return months
  }),
  yearsText: computed(() => {
    let years = ""
    const cronEvery = state.year.cronEvery
    switch (cronEvery.toString()) {
      case "1":
        years = "*"
        break
      case "2":
        years = state.year.incrementStart + "/" + state.year.incrementIncrement
        break
      case "3":
        state.year.specificSpecific.map((val: string) => {
          years += val + ","
        })
        years = years.slice(0, -1)
        break
      case "4":
        years = state.year.rangeStart + "-" + state.year.rangeEnd
        break
    }
    return years
  }),
  cron: computed(() => {
    const cron = (
      (showSecond.value ? `${state.secondsText || "*"}` : "") +
      ` ${state.minutesText || "*"} ${state.hoursText || "*"} ${
        state.daysText || "*"
      } ${state.monthsText || "*"} ${state.weeksText || "?"} ` +
      (showYear.value ? `${state.yearsText || "*"}` : "")
    ).trim()
    emit("changeCron", cron)
    return cron
  })
})

const close = () => {
  emit("close")
}
const handleChange = () => {
  emit("changeCron", state.cron)
  close()
}

// const _rest = (data) => {
//   for (const i in data) {
//     if (data[i] instanceof Object) {
//       _rest(data[i])
//     } else {
//       switch (typeof data[i]) {
//         case "object":
//           data[i] = []
//           break
//         case "string":
//           data[i] = ""
//           break
//       }
//     }
//   }
// }
</script>
<template>
  <perfect-scrollbar>
    <div class="vue3-cron-div">
      <div style="margin-bottom: 20px">
        <a-radio-group v-model:value="sysRadio">
          <a-radio-button value="1" size="small">Linux</a-radio-button>
          <a-radio-button value="2" size="small">Java(Spring)</a-radio-button>
          <a-radio-button value="3" size="small">Java(Quartz)</a-radio-button>
        </a-radio-group>
      </div>
      <a-tabs>
        <a-tab-pane v-if="showSecond" :key="1">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Seconds.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.second.cronEvery">
            <a-form>
              <a-form-item>
                <a-radio value="1">{{ state.text?.Seconds.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Seconds.interval[0] }}
                  <a-input-number size="small" v-model:value="state.second.incrementIncrement" :min="1" :max="60" />
                  {{ state.text?.Seconds.interval[1] || "" }}
                  <a-input-number size="small" v-model:value="state.second.incrementStart" :min="0" :max="59" />
                  {{ state.text?.Seconds.interval[2] || "" }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" value="3">{{ state.text?.Seconds.specific }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.second.specificSpecific" style="width: 40%">
                  <a-select-option v-for="(val, index) in 60" :key="'1-' + index" :value="val - 1"
                    >{{ val - 1 }}
                  </a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="4"
                  >{{ state.text?.Seconds.cycle[0] }}
                  <a-input-number size="small" v-model:value="state.second.rangeStart" :min="1" :max="60" />
                  {{ state.text?.Seconds.cycle[1] || "" }}
                  <a-input-number size="small" v-model:value="state.second.rangeEnd" :min="0" :max="59" />
                  {{ state.text?.Seconds.cycle[2] || "" }}
                </a-radio>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
        <a-tab-pane :key="2">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Minutes.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.minute.cronEvery">
            <a-form class="tabBody VueScroller" :style="{ 'max-height': maxHeight }">
              <a-form-item>
                <a-radio value="1">{{ state.text?.Minutes.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Minutes.interval[0] }}
                  <a-input-number size="small" v-model:value="state.minute.incrementIncrement" :min="1" :max="60" />
                  {{ state.text?.Minutes.interval[1] }}
                  <a-input-number size="small" v-model:value="state.minute.incrementStart" :min="0" :max="59" />
                  {{ state.text?.Minutes.interval[2] || "" }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" value="3">{{ state.text?.Minutes.specific }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.minute.specificSpecific" style="width: 40%">
                  <a-select-option v-for="(val, index) in 60" :key="'2-' + index" :value="val - 1"
                    >{{ val - 1 }}
                  </a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="4"
                  >{{ state.text?.Minutes.cycle[0] }}
                  <a-input-number size="small" v-model:value="state.minute.rangeStart" :min="1" :max="60" />
                  {{ state.text?.Minutes.cycle[1] }}
                  <a-input-number size="small" v-model:value="state.minute.rangeEnd" :min="0" :max="59" />
                  {{ state.text?.Minutes.cycle[2] }}
                </a-radio>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
        <a-tab-pane :key="3">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Hours.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.hour.cronEvery">
            <a-form class="tabBody VueScroller" :style="{ 'max-height': maxHeight }">
              <a-form-item>
                <a-radio value="1">{{ state.text?.Hours.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Hours.interval[0] }}
                  <a-input-number size="small" v-model:value="state.hour.incrementIncrement" :min="0" :max="23" />
                  {{ state.text?.Hours.interval[1] }}
                  <a-input-number size="small" v-model:value="state.hour.incrementStart" :min="0" :max="23" />
                  {{ state.text?.Hours.interval[2] }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" :value="3">{{ state.text?.Hours.specific }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.hour.specificSpecific" style="width: 40%">
                  <a-select-option v-for="(val, index) in 24" :key="'3-' + index" :value="val - 1"
                    >{{ val - 1 }}
                  </a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="4"
                  >{{ state.text?.Hours.cycle[0] }}
                  <a-input-number size="small" v-model:value="state.hour.rangeStart" :min="0" :max="23" />
                  {{ state.text?.Hours.cycle[1] }}
                  <a-input-number size="small" v-model:value="state.hour.rangeEnd" :min="0" :max="23" />
                  {{ state.text?.Hours.cycle[2] }}
                </a-radio>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
        <a-tab-pane :key="4">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Day.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.day.cronEvery">
            <a-form class="tabBody VueScroller" :style="{ 'max-height': maxHeight }">
              <a-form-item>
                <a-radio value="1">{{ state.text?.Day.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Day.intervalWeek[0] }}
                  <a-input-number size="small" v-model:value="state.week.incrementIncrement" :min="1" :max="7" />
                  {{ state.text?.Day.intervalWeek[1] }}
                  {{ state.text?.Day.intervalWeek[2] }}
                </a-radio>
                <a-select size="small" v-model:value="state.week.incrementStart" style="width: 40%">
                  <a-select-option
                    v-for="(val, index) in 7"
                    :key="'4-' + index"
                    :label="state.text?.Week[val - 1]"
                    :value="val"
                  />
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="3"
                  >{{ state.text?.Day.intervalDay[0] }}
                  <a-input-number size="small" v-model:value="state.day.incrementIncrement" :min="1" :max="31" />
                  {{ state.text?.Day.intervalDay[1] }}
                  <a-input-number size="small" v-model:value="state.day.incrementStart" :min="1" :max="31" />
                  {{ state.text?.Day.intervalDay[2] }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" :value="4">{{ state.text?.Day.specificWeek }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.week.specificSpecific" style="width: 40%">
                  <a-select-option
                    v-for="(val, index) in 7"
                    :key="'5-' + index"
                    :label="state.text?.Week[val - 1]"
                    :value="['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'][val - 1]"
                  />
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" :value="5">{{ state.text?.Day.specificDay }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.day.specificSpecific" style="width: 40%">
                  <a-select-option v-for="(val, index) in 31" :key="index" :value="val">{{ val }}</a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="6">{{ state.text?.Day.lastDay }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="7">{{ state.text?.Day.lastWeekday }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="8"
                  >{{ state.text?.Day.lastWeek[0] }}
                  {{ state.text?.Day.lastWeek[1] || "" }}
                </a-radio>
                <a-select size="small" v-model:value="state.day.cronLastSpecificDomDay" style="width: 40%">
                  <a-select-option
                    v-for="(val, index) in 7"
                    :key="'6-' + index"
                    :label="state.text?.Week[val - 1]"
                    :value="val"
                  />
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="9">
                  <a-input-number size="small" v-model:value="state.day.cronDaysBeforeEomMinus" :min="1" :max="31" />
                  {{ state.text?.Day.beforeEndMonth[0] }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="10"
                  >{{ state.text?.Day.nearestWeekday[0] }}
                  <a-input-number size="small" v-model:value="state.day.cronDaysNearestWeekday" :min="1" :max="31" />
                  {{ state.text?.Day.nearestWeekday[1] }}
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="11"
                  >{{ state.text?.Day.someWeekday[0] }}
                  <a-input-number size="small" v-model:value="state.week.cronNthDayNth" :min="1" :max="5" />
                  {{ state.text?.Day.someWeekday[1] }}
                </a-radio>
                <a-select size="small" v-model:value="state.week.cronNthDayDay" style="width: 40%">
                  <a-select-option
                    v-for="(val, index) in 7"
                    :key="'7-' + index"
                    :label="state.text?.Week[val - 1]"
                    :value="val"
                  />
                </a-select>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
        <a-tab-pane :key="5">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Month.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.month.cronEvery">
            <a-form class="tabBody VueScroller" :style="{ 'max-height': props.maxHeight }">
              <a-form-item>
                <a-radio value="1">{{ state.text?.Month.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Month.interval[0] }}
                  <a-input-number size="small" v-model:value="state.month.incrementIncrement" :min="0" :max="12" />
                  {{ state.text?.Month.interval[1] }}
                  <a-input-number size="small" v-model:value="state.month.incrementStart" :min="0" :max="12" />
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" :value="3">{{ state.text?.Month.specific }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.month.specificSpecific" style="width: 40%">
                  <a-select-option v-for="(val, index) in 12" :key="'8-' + index" :label="val" :value="val" />
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="4"
                  >{{ state.text?.Month.cycle[0] }}
                  <a-input-number size="small" v-model:value="state.month.rangeStart" :min="1" :max="12" />
                  {{ state.text?.Month.cycle[1] }}
                  <a-input-number size="small" v-model:value="state.month.rangeEnd" :min="1" :max="12" />
                </a-radio>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
        <a-tab-pane v-if="showYear" :key="6">
          <template #tab>
            <a-button type="text" class="el-icon-date">{{ state.text?.Year.name }}</a-button>
          </template>
          <a-radio-group v-model:value="state.year.cronEvery">
            <a-form class="tabBody VueScroller" :style="{ 'max-height': maxHeight }">
              <a-form-item>
                <a-radio value="1">{{ state.text?.Year.every }}</a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio value="2"
                  >{{ state.text?.Year.interval[0] }}
                  <a-input-number size="small" v-model:value="state.year.incrementIncrement" :min="1" :max="99" />
                  {{ state.text?.Year.interval[1] }}
                  <a-input-number size="small" v-model:value="state.year.incrementStart" :min="2018" :max="2118" />
                </a-radio>
              </a-form-item>
              <a-form-item>
                <a-radio class="long" :value="3">{{ state.text?.Year.specific }}</a-radio>
                <a-select size="small" mode="multiple" v-model:value="state.year.specificSpecific" style="width: 40%">
                  <a-select-option
                    v-for="(val, index) in 100"
                    :key="'9-' + index"
                    :label="2017 + val"
                    :value="2017 + val"
                  />
                </a-select>
              </a-form-item>
              <a-form-item>
                <a-radio value="4"
                  >{{ state.text?.Year.cycle[0] }}
                  <a-input-number size="small" v-model:value="state.year.rangeStart" :min="2018" :max="2118" />
                  {{ state.text?.Year.cycle[1] }}
                  <a-input-number size="small" v-model:value="state.year.rangeEnd" :min="2018" :max="2118" />
                </a-radio>
              </a-form-item>
            </a-form>
          </a-radio-group>
        </a-tab-pane>
      </a-tabs>
      <div class="bottom">
        <div class="value">
          <span> cron预览: </span>
          <a-tag type="success">
            {{ state.cron }}
          </a-tag>
        </div>
        <div class="buttonDiv">
          <a-radio-button type="primary" size="small" @click.stop="handleChange">{{ state.text?.Save }}</a-radio-button>
          <a-radio-button type="primary" size="small" @click="close">{{ state.text?.Close }}</a-radio-button>
        </div>
      </div>
    </div>
  </perfect-scrollbar>
</template>
<style lang="scss">
.el-icon-date {
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.vue3-cron-div {
  .a-input-number__decrease,
  .a-input-number__increase {
    top: 2px !important;
  }

  .language {
    position: absolute;
    right: 25px;
    z-index: 1;
  }

  .a-tabs {
    box-shadow: none;
  }

  .tabBody {
    overflow: auto;

    .a-row {
      margin: 20px 0;

      .long {
        .a-select {
          width: 350px;
        }
      }

      .a-input-number {
        width: 110px;
      }
    }
  }

  .ant-radio-group {
    display: inline;
  }

  .bottom {
    width: 100%;
    margin-top: 5px;
    display: flex;
    align-items: center;
    justify-content: space-around;

    .value {
      float: left;
      font-size: 14px;
      vertical-align: middle;

      span:nth-child(1) {
        color: red;
      }
    }
  }
}
</style>
