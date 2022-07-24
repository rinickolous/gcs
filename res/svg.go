/*
 * Copyright ©1998-2022 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package res

import (
	"github.com/richardwilkes/toolbox/log/jot"
	"github.com/richardwilkes/unison"
)

// Various SVG images.
var (
	AttributesSVG              = mustSVG(512, 512, "M256.1 246c-13.25 0-23.1 10.75-23.1 23.1c1.125 72.25-8.124 141.9-27.75 211.5C201.7 491.3 206.6 512 227.5 512c10.5 0 20.12-6.875 23.12-17.5c13.5-47.87 30.1-125.4 29.5-224.5C280.1 256.8 269.4 246 256.1 246zM255.2 164.3C193.1 164.1 151.2 211.3 152.1 265.4c.75 47.87-3.75 95.87-13.37 142.5c-2.75 12.1 5.624 25.62 18.62 28.37c12.1 2.625 25.62-5.625 28.37-18.62c10.37-50.12 15.12-101.6 14.37-152.1C199.7 238.6 219.1 212.1 254.5 212.3c31.37 .5 57.24 25.37 57.62 55.5c.8749 47.1-2.75 96.25-10.62 143.5c-2.125 12.1 6.749 25.37 19.87 27.62c19.87 3.25 26.75-15.12 27.5-19.87c8.249-49.1 12.12-101.1 11.25-151.1C359.2 211.1 312.2 165.1 255.2 164.3zM144.6 144.5C134.2 136.1 119.2 137.6 110.7 147.9C85.25 179.4 71.38 219.3 72 259.9c.6249 37.62-2.375 75.37-8.999 112.1c-2.375 12.1 6.249 25.5 19.25 27.87c20.12 3.5 27.12-14.87 27.1-19.37c7.124-39.87 10.5-80.62 9.749-121.4C119.6 229.3 129.2 201.3 147.1 178.3C156.4 167.9 154.9 152.9 144.6 144.5zM253.1 82.14C238.6 81.77 223.1 83.52 208.2 87.14c-12.87 2.1-20.87 15.1-17.87 28.87c3.125 12.87 15.1 20.75 28.1 17.75C230.4 131.3 241.7 130 253.4 130.1c75.37 1.125 137.6 61.5 138.9 134.6c.5 37.87-1.375 75.1-5.624 113.6c-1.5 13.12 7.999 24.1 21.12 26.5c16.75 1.1 25.5-11.87 26.5-21.12c4.625-39.75 6.624-79.75 5.999-119.7C438.6 165.3 355.1 83.64 253.1 82.14zM506.1 203.6c-2.875-12.1-15.51-21.25-28.63-18.38c-12.1 2.875-21.12 15.75-18.25 28.62c4.75 21.5 4.875 37.5 4.75 61.62c-.1249 13.25 10.5 24.12 23.75 24.25c13.12 0 24.12-10.62 24.25-23.87C512.1 253.8 512.3 231.8 506.1 203.6zM465.1 112.9c-48.75-69.37-128.4-111.7-213.3-112.9c-69.74-.875-134.2 24.84-182.2 72.96c-46.37 46.37-71.34 108-70.34 173.6l-.125 21.5C-.3651 281.4 10.01 292.4 23.26 292.8C23.51 292.9 23.76 292.9 24.01 292.9c12.1 0 23.62-10.37 23.1-23.37l.125-23.62C47.38 193.4 67.25 144 104.4 106.9c38.87-38.75 91.37-59.62 147.7-58.87c69.37 .1 134.7 35.62 174.6 92.37c7.624 10.87 22.5 13.5 33.37 5.875C470.1 138.6 473.6 123.8 465.1 112.9z")
	BackSVG                    = mustSVG(256, 512, "M137.4 406.6 9.4 279.5C3.125 272.4 0 264.2 0 255.1s3.125-16.38 9.375-22.63l128-127.1c9.156-9.156 22.91-11.9 34.88-6.943S192 115.1 192 128v255.1c0 12.94-7.781 24.62-19.75 29.58s-25.75 3.12-34.85-6.08z")
	BodyTypeSVG                = mustSVG(320, 512, "M208 48c0 26.51-21.5 48-48 48s-48-21.49-48-48 21.5-48 48-48 48 21.49 48 48zm-56 304v128c0 17.7-14.3 32-32 32s-32-14.3-32-32V256.9l-28.57 47.6c-9.1 15.1-28.76 20-43.91 10.9-15.15-9.1-20.051-28.7-10.947-43.9l58.277-96.9C80.2 145.7 111.4 128 145.1 128h29.8c33.7 0 64.9 17.7 82.3 46.6l58.2 96.9c9.1 15.2 4.2 34.8-10.9 43.9-15.2 9.1-34.8 4.2-43.9-10.9L232 256.9V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V352h-16z")
	BookmarkSVG                = mustSVG(384, 512, "M384 48v464L192 400 0 512V48C0 21.5 21.5 0 48 0h288c26.5 0 48 21.5 48 48z")
	CheckmarkSVG               = mustSVG(172, 172, "m149.285 31.294-11.86-8.063c-3.283-2.222-7.779-1.37-9.975 1.887l-58.143 85.741-26.72-26.72c-2.791-2.79-7.34-2.79-10.13 0L22.3 94.295c-2.79 2.79-2.79 7.339 0 10.156l41.088 41.087c2.3 2.3 5.917 4.058 9.173 4.058 3.257 0 6.538-2.042 8.657-5.117l69.979-103.236c2.222-3.256 1.37-7.727-1.913-9.95z")
	CircledAddSVG              = mustSVG(512, 512, "M0 256C0 114.6 114.6 0 256 0s256 114.6 256 256-114.6 256-256 256S0 397.4 0 256zm256 112c13.3 0 24-10.7 24-24v-64h64c13.3 0 24-10.7 24-24s-10.7-24-24-24h-64v-64c0-13.3-10.7-24-24-24s-24 10.7-24 24v64h-64c-13.3 0-24 10.7-24 24s10.7 24 24 24h64v64c0 13.3 10.7 24 24 24z")
	CircledVerticalEllipsisSVG = mustSVG(512, 512, "M256 0C114.6 0 0 114.6 0 256s114.6 256 256 256 256-114.6 256-256S397.4 0 256 0zm0 40c30.93 0 56 25.07 56 56 0 30.9-25.07 56-56 56s-56-25.1-56-56c0-30.93 25.07-56 56-56zm0 160c30.93 0 56 25.1 56 56s-25.07 56-56 56-56-25.1-56-56 25.07-56 56-56zm0 160c30.93 0 56 25.1 56 56s-25.07 56-56 56-56-25.1-56-56 25.07-56 56-56z")
	ClosedFolderSVG            = mustSVG(512, 512, "M464 128H272l-64-64H48C21.49 64 0 85.49 0 112v288c0 26.51 21.49 48 48 48h416c26.51 0 48-21.49 48-48V176c0-26.51-21.49-48-48-48z")
	CoinsSVG                   = mustSVG(512, 512, "M512 80c0 18.01-14.3 34.6-38.4 48-29.1 16.1-72.4 27.5-122.3 30.9-3.6-1.7-7.4-3.4-11.2-5C300.6 137.4 248.2 128 192 128c-8.3 0-16.4.2-24.5.6l-1.1-.6C142.3 114.6 128 98.01 128 80c0-44.18 85.1-80 192-80 106 0 192 35.82 192 80zm-351.3 81.1c10.2-.7 20.6-1.1 31.3-1.1 62.2 0 117.4 12.3 152.5 31.4 24.8 13.5 39.5 30.3 39.5 48.6 0 3.1-.7 7.9-2.1 11.7-4.6 13.2-17.8 25.3-35 35.6-.1 0-.3.1-.4.2-.3.2-.6.3-.9.5-35 19.4-90.8 32-153.6 32-59.6 0-112.94-11.3-148.16-29.1-1.87-1-3.69-2.8-5.45-2.9C14.28 274.6 0 258 0 240c0-34.8 53.43-64.5 128-75.4 10.5-1.6 21.4-2.8 32.7-3.5zm231.2 25.5c28.3-4.4 54.2-11.4 76.2-20.5 16.3-6.8 31.4-15.2 43.9-25.5V176c0 19.3-16.5 37.1-43.8 50.9-14.7 7.4-32.4 13.6-52.4 18.4.1-1.7.2-3.5.2-5.3 0-21.9-10.6-39.9-24.1-53.4zM384 336c0 18-14.3 34.6-38.4 48-1.8.1-3.6 1.9-5.4 2.9C304.9 404.7 251.6 416 192 416c-62.8 0-118.58-12.6-153.61-32C14.28 370.6 0 354 0 336v-35.4c12.45 10.3 27.62 18.7 43.93 25.5C83.44 342.6 135.8 352 192 352c56.2 0 108.6-9.4 148.1-25.9 7.8-3.2 15.3-6.9 22.4-10.9 6.1-3.4 11.8-7.2 17.2-11.2 1.5-1.1 2.9-2.3 4.3-3.4V336zm32-57.9c18.1-5 36.5-9.5 52.1-16 16.3-6.8 31.4-15.2 43.9-25.5V272c0 10.5-5 21-14.9 30.9-16.3 16.3-45 29.7-81.3 38.4.1-1.7.2-3.5.2-5.3v-57.9zM192 448c56.2 0 108.6-9.4 148.1-25.9 16.3-6.8 31.4-15.2 43.9-25.5V432c0 44.2-86 80-192 80C85.96 512 0 476.2 0 432v-35.4c12.45 10.3 27.62 18.7 43.93 25.5C83.44 438.6 135.8 448 192 448z")
	DownloadSVG                = mustSVG(512, 512, "M216 0h80c13.3 0 24 10.7 24 24v168h87.7c17.8 0 26.7 21.5 14.1 34.1L269.7 378.3c-7.5 7.5-19.8 7.5-27.3 0L90.1 226.1c-12.6-12.6-3.7-34.1 14.1-34.1H192V24c0-13.3 10.7-24 24-24zm296 376v112c0 13.3-10.7 24-24 24H24c-13.3 0-24-10.7-24-24V376c0-13.3 10.7-24 24-24h146.7l49 49c20.1 20.1 52.5 20.1 72.6 0l49-49H488c13.3 0 24 10.7 24 24zm-124 88c0-11-9-20-20-20s-20 9-20 20 9 20 20 20 20-9 20-20zm64 0c0-11-9-20-20-20s-20 9-20 20 9 20 20 20 20-9 20-20z")
	FirstSVG                   = mustSVG(512, 512, "M0 415.1V96.03c0-17.67 14.33-31.1 31.1-31.1 18.57-.9 32.9 13.43 32.9 31.1v131.8l171.5-156.5c20.6-17.05 52.5-2.67 52.5 24.7v131.9l171.5-156.5c20.6-17.15 52.5-2.77 52.5 24.6v319.9c0 27.37-31.88 41.74-52.5 24.62L288 285.2v130.7c0 27.37-31.88 41.74-52.5 24.62L64 285.2v130.7c0 17.67-14.33 31.1-31.1 31.1-18.57.1-32.9-13.4-32.9-31.9z")
	ForwardSVG                 = mustSVG(256, 512, "m118.6 105.4 128 127.1c6.3 7.1 9.4 15.3 9.4 22.6s-3.125 16.38-9.375 22.63l-128 127.1c-9.156 9.156-22.91 11.9-34.88 6.943S64 396.9 64 383.1V128c0-12.94 7.781-24.62 19.75-29.58s25.75-2.19 34.85 6.98z")
	GCSTraitsSVG               = mustSVG(512, 512, "M79.625 22.03c-16.694.274-31.01 5.33-41.22 15.658C5.743 70.735 27.53 145.313 87.22 204.313c39.992 39.53 91.568 45.025 125.03 56.593-38.19 35.214-80.874 67.594-130.438 99.28l61.594 60.876c33.267-53.395 68.052-99.412 106.406-140.593 66.466 44.55 113.05 126.476 157.594 206.967l85.5-86.5c-82.206-44.252-164.58-88.96-209.25-154.687 41.214-39.214 86.72-74.14 138.656-107.344L360.72 78.03c-30.47 48.903-61.926 91.685-96.845 130.564-11.704-33.438-18.262-84.475-58.28-124.032C164.556 44 116.35 21.43 79.624 22.032zm16.97 47.064c20.94.415 50.89 16.01 77.436 42.25 36.934 36.505 53.305 79.782 36.595 96.687-16.71 16.907-60.194 1.037-97.125-35.468C76.57 136.06 60.165 92.75 76.875 75.844c4.7-4.755 11.525-6.913 19.72-6.75z")
	GCSTraitModifiersSVG       = mustSVG(512, 512, "M321.375 15.313 262.72 73.906l25.78 6.906-15.563 58.063a115.75 115.75 0 0 0-16.25-1.125c-.887.003-1.77.04-2.656.063l21.97 45.75 42.25-28.407a115.69 115.69 0 0 0-20.03-9.875l15.467-57.718 28.657 7.657-20.97-79.907zM133.25 40.063l-.094 82.906 23.125-13.345 30.064 52.063a116.984 116.984 0 0 0-14.125 12.687l50.06 16.438 9.064-50.157a117.56 117.56 0 0 0-22.594 7.625l-29.875-51.718 25.688-14.812-71.313-41.688zm255.28 90.593 13.345 23.094-52.063 30.063c-3.8-5.002-8.01-9.707-12.593-14.063l-16.126 48.156 49.28 8.938a117.279 117.279 0 0 0-7.155-20.594l51.717-29.875 14.813 25.656 41.688-71.31-82.907-.064zm-290.78 38.5-79.906 20.97 58.562 58.655L83.312 223l58.063 15.563a115.444 115.444 0 0 0-1 20.156l47.53-22.814-29.843-43.25a115.706 115.706 0 0 0-10.218 20.625l-57.78-15.468 7.686-28.656zm275.875 81.28L328.5 272.813l28.313 42.125a116.05 116.05 0 0 0 8.28-16.437l57.938 15.53-6.905 25.783 80.063-21.532-58.72-58.092-7.687 28.656-57.592-15.438c1.27-7.706 1.707-15.387 1.437-22.97zm-230.28 30.283c1.5 6.44 3.516 12.72 6.06 18.78l-52.093 30.094L83.97 306.5l-41.376 71.813 82.594-.438-14.813-25.656 51.78-29.908a117.454 117.454 0 0 0 15.907 18.032l17.282-49.53-52-10.095zM294 316.75l-9.22 51.03a116.57 116.57 0 0 0 17.25-5.686l30.095 52.094L309 427.53l71.844 41.408-.438-82.625-25.687 14.843-29.876-51.75a116.759 116.759 0 0 0 17.844-15.687L294 316.75zM240.25 324l-44.125 30.03A115.392 115.392 0 0 0 213 362.563L197.47 420.5l-25.782-6.906 21.53 80.062 58.095-58.72-28.625-7.686 15.437-57.625a116.068 116.068 0 0 0 24.72 1.406L240.25 324z")
	GCSEquipmentSVG            = mustSVG(512, 512, "M262.406 17.188c-27.22 8.822-54.017 28.012-72.375 55.53 17.544 47.898 17.544 57.26 0 105.157 19.92 15.463 40.304 24.76 60.782 27.47-2.063-25.563-3.63-51.13 1.125-76.69-13.625-1.483-23.374-5.995-37-13.874V82.563c35.866 19.096 61.84 18.777 98.813 0v32.22c-13.364 6.497-21.886 11.16-35.25 13.218 3.614 25.568 3.48 51.15 1.375 76.72 18.644-3.265 37.236-12.113 55.5-26.845-14.353-47.897-14.355-57.26 0-105.156-16.982-28.008-47.453-46.633-72.97-55.532zm-129.594 8.218c-25.906 110.414-27.35 215.33-27.4 330.922-18.84-1.537-37.582-5.12-56.027-11.12v28.554h69.066c8.715 35.025 6.472 70.052-1.036 105.078h28.13c-7.195-35.026-8.237-70.053-.872-105.078h68.904v-28.555c-18.49 4.942-37.256 8.552-56.097 10.46.082-114.94 2.496-223.068-24.667-330.26zm89.47 202.375c0 117.27 25.517 233.342 120.155 257.97C446.62 464.716 462.72 345.374 462.72 227.78H222.28z")
	GCSEquipmentModifiersSVG   = mustSVG(512, 512, "m265.344 17.5-4.188 25.313a212.701 212.701 0 0 0-52.562 6.28l-9.438-25.062-55.125 20.75 9.907 26.282a215.577 215.577 0 0 0-41.5 30.876L90 83.53l-37.375 45.5L75.75 148a212.944 212.944 0 0 0-20.5 46.594l-30.063-4.97-9.593 58.095L46 252.75c-.374 17.218 1.313 34.127 4.906 50.438L22 314.063l20.75 55.125 28.625-10.782a215.542 215.542 0 0 0 29.28 41.75L81.688 423.25l45.532 37.344 18.343-22.344c14.386 9.118 30.04 16.577 46.687 22.125l-4.53 27.5 58.093 9.594 4.343-26.283c18.046.874 35.764-.54 52.875-4.03l8.97 23.78 55.094-20.75-8.53-22.656a215.44 215.44 0 0 0 44.655-30.78l17.936 14.72 37.344-45.533-17.188-14.093a212.909 212.909 0 0 0 23.25-50.03l21.407 3.53 9.56-58.094-21.06-3.47a212.68 212.68 0 0 0-5.408-55.06l19.844-7.47-20.75-55.125-20.187 7.594a215.574 215.574 0 0 0-32.5-44.376l14.155-17.25-45.5-37.375-14.72 17.936c-15.396-9.116-32.13-16.37-49.936-21.47l3.967-24.092-58.093-9.594zm-8.03 47.938A191.66 191.66 0 0 1 291.124 68C395.113 85.164 465.665 183.606 448.5 287.594 431.336 391.58 332.894 462.134 228.906 444.97 124.92 427.803 54.366 329.36 71.53 225.374c15.02-90.99 92.292-156.386 181.032-159.813 1.585-.06 3.16-.103 4.75-.124zm.217 18.687c-1.437.018-2.88.04-4.31.094-80.154 3.037-149.672 61.917-163.25 144.186-15.52 94.022 47.977 182.606 142 198.125 94.02 15.52 182.573-47.977 198.093-142 15.52-94.02-47.947-182.573-141.97-198.092a173.956 173.956 0 0 0-30.562-2.313zm.408 18.156c9-.116 18.145.546 27.343 2.064 84.096 13.88 140.85 93.092 126.97 177.187-13.88 84.096-93.06 140.85-177.156 126.97-25.808-4.26-49.03-14.68-68.438-29.5l109.688-133.625 52.844 43.375 58.437-71.188-108.22-88.78-101.842 35.53 71 58.25L140.78 353.938c-26.985-33.066-40.165-77.126-32.655-122.625 12.146-73.583 74.283-126.223 145.97-128.937 1.28-.048 2.557-.077 3.843-.094z")
	GCSNotesSVG                = mustSVG(384, 512, "M240.03 35.938c-1.08.01-2.168.062-3.25.124-8.644.502-17.16 2.8-22.5 5.97-5.336 3.167-7.018 5.72-6.81 9.593v.25l.78 28.156 59.97-1.28-.876-31.844c-.148-3.014-1.806-5.15-7.47-7.593C255.63 37.48 249.63 36.27 243.25 36a61.354 61.354 0 0 0-3.22-.063zm224.94 57.218L33.593 102.53l1.375 62 154.655 4.064-148.156 9.72-2.907 98.81 1.406.313 8.06 1.844-.843 8.22-6.906 67.47 429.533-9.283L464.219 283l-140.376-3.656 139.22-9.156-8.877-99.407-138.875-3.624 151.032-9.937-1.375-64.064zM276.31 368.562l-59.875 1.282L220 495.78h59.844l-3.53-127.217z")
	GCSSheetSVG                = mustSVG(512, 512, "M255 45.4c-24.5 0-47 11.8-63.9 33.4-16.9 21.5-27.1 52.6-27.1 86.5 0 36 12.1 67.5 31 89.5l13.5 15-19.6 4.6c-52.3 11.9-77.4 36.9-91.75 75.2-13.7 35.7-15.6 84.8-16.1 143.3H431c-.2-58.7-.5-109.3-13-145.5-13.4-39.4-37.9-64.3-94-75.4l-19.9-3.7 12.9-15.7c17.7-21.9 28.8-52.6 28.8-87.5 0-33.9-10.3-64.9-27.2-86.3-16.8-21.7-39.3-33.6-63.6-33.4z")
	GCSSkillsSVG               = mustSVG(512, 512, "M119.1 25v.1c-25 3.2-47.1 32-47.1 68.8 0 20.4 7.1 38.4 17.5 50.9L99.7 157 84 159.9c-13.7 2.6-23.8 9.9-32.2 21.5-8.5 11.5-14.9 27.5-19.4 45.8-8.2 33.6-9.9 74.7-10.1 110.5h44l11.9 158.4h96.3L185 337.7h41.9c0-36.2-.3-77.8-7.8-111.7-4-18.5-10.2-34.4-18.7-45.9-8.6-11.4-19.2-18.7-34.5-21l-16-2.5L160 144c10-12.5 16.7-30.2 16.7-50.1 0-39.2-24.8-68.8-52.4-68.8-2.9 0-4.7-.1-5.2-.1zM440 33c-17.2 0-31 13.77-31 31s13.8 31 31 31 31-13.77 31-31-13.8-31-31-31zM311 55v48H208v18h103v158h-55v18h55v110H208v18h103v32h80.8c-.5-2.9-.8-5.9-.8-9 0-3.1.3-6.1.8-9H329V297h62.8c-.5-2.9-.8-5.9-.8-9 0-3.1.3-6.1.8-9H329V73h62.8c-.5-2.92-.8-5.93-.8-9 0-3.07.3-6.08.8-9H311zm129 202c-17.2 0-31 13.8-31 31s13.8 31 31 31 31-13.8 31-31-13.8-31-31-31zm0 160c-17.2 0-31 13.8-31 31s13.8 31 31 31 31-13.8 31-31-13.8-31-31-31z")
	GCSSpellsSVG               = mustSVG(512, 512, "M103.432 17.844c-1.118.005-2.234.032-3.348.08-2.547.11-5.083.334-7.604.678-20.167 2.747-39.158 13.667-52.324 33.67-24.613 37.4 2.194 98.025 56.625 98.025.536 0 1.058-.012 1.583-.022v.704h60.565c-10.758 31.994-30.298 66.596-52.448 101.43-2.162 3.4-4.254 6.878-6.29 10.406l34.878 35.733-56.263 9.423c-32.728 85.966-27.42 182.074 48.277 182.074v-.002l9.31.066c23.83-.57 46.732-4.298 61.325-12.887 4.174-2.458 7.63-5.237 10.467-8.42h-32.446c-20.33 5.95-40.8-6.94-47.396-25.922-8.956-25.77 7.52-52.36 31.867-60.452 5.803-1.93 11.723-2.834 17.565-2.834v-.406h178.33c-.57-44.403 16.35-90.125 49.184-126 23.955-26.176 42.03-60.624 51.3-94.846l-41.225-24.932 38.272-6.906-43.37-25.807h-.005l.002-.002.002.002 52.127-8.85c-5.232-39.134-28.84-68.113-77.37-68.113C341.14 32.26 222.11 35.29 149.34 28.496c-14.888-6.763-30.547-10.723-45.908-10.652zm.464 18.703c13.137.043 27.407 3.804 41.247 10.63l.033-.07c4.667 4.735 8.542 9.737 11.68 14.985H82.92l10.574 14.78c10.608 14.83 19.803 31.99 21.09 42.024.643 5.017-.11 7.167-1.814 8.836-1.705 1.67-6.228 3.875-15.99 3.875-40.587 0-56.878-44.952-41.012-69.06C66.238 46.64 79.582 39.22 95.002 37.12c2.89-.395 5.863-.583 8.894-.573zM118.5 80.78h46.28c4.275 15.734 3.656 33.07-.544 51.51H131.52c1.9-5.027 2.268-10.574 1.6-15.77-1.527-11.913-7.405-24.065-14.62-35.74zm101.553 317.095c6.44 6.84 11.192 15.31 13.37 24.914 3.797 16.736 3.092 31.208-1.767 43.204-4.526 11.175-12.576 19.79-22.29 26h237.19c14.448 0 24.887-5.678 32.2-14.318 7.312-8.64 11.2-20.514 10.705-32.352-.186-4.473-.978-8.913-2.407-13.18l-69.91-8.205 42.017-20.528c-8.32-3.442-18.64-5.537-31.375-5.537H220.053zm-42.668.506c-1.152-.003-2.306.048-3.457.153-2.633.242-5.256.775-7.824 1.63-15.11 5.02-25.338 21.54-20.11 36.583 3.673 10.57 15.347 17.71 25.654 13.938l1.555-.57h43.354c.946-6.36.754-13.882-1.358-23.192-3.71-16.358-20.543-28.483-37.815-28.54z")
	GCSTemplateSVG             = mustSVG(512, 512, "M250.322 18.494c-25.06 3.26-47.158 32.267-47.158 69.346 0 20.453 7.06 38.57 17.502 51.166l10.123 12.213-15.59 2.932c-13.676 2.574-23.794 9.896-32.272 21.547-8.48 11.65-14.86 27.7-19.326 46.095-8.23 33.9-9.916 75.216-10.143 111.275h44.007l11.883 159.512h96.37l10.514-159.512h41.88c-.013-36.448-.353-78.316-7.81-112.48-4.042-18.524-10.176-34.575-18.777-46.12-8.6-11.543-19.21-18.81-34.482-21.18l-15.912-2.468 10.037-12.59c9.99-12.533 16.7-30.436 16.7-50.392 0-39.537-24.776-69.268-52.352-69.268-2.915 0-4.754-.135-5.196-.078zm178.608 1.078c-31.872-.534-61.166 26.473-71.084 63.49-4.575 17.073-4.83 35.29-.817 51.108-10.96 1.307-20.99 5.173-29.772 10.996 5.563 3.58 10.537 7.906 14.906 12.814 7.998-4.296 16.716-6.28 27.084-5.492l15.816 1.2-6.615-14.415c-5.86-12.764-7.33-33.55-2.554-51.377 8.122-30.308 31.484-49.75 52.75-49.61 1.416.008 2.825.104 4.22.29l.01.002c.263.037 1.817.567 4.44 1.27 23.73 6.36 38.404 37.853 29.168 72.324-4.66 17.392-15.965 34.567-27.02 42.73l-12.954 9.565 14.73 6.502c13.063 5.765 20.835 13.86 25.885 24.348 5.05 10.487 7.12 23.674 6.846 38.674-.5 27.368-8.862 60.148-17.2 91.362l-36.864-9.88-51.232 153.712-42.69.11-1.23 18.69 57.402-.146 49.914-149.758 37.946 10.166 2.42-9.025c9.022-33.677 19.603-71.135 20.22-104.89.31-16.876-1.89-32.994-8.693-47.124-5.016-10.417-12.696-19.57-23.065-26.622 10.814-11.607 19.228-27.125 23.637-43.58 11.288-42.13-6.228-85.52-42.38-95.21l-.003-.003c-1.106-.296-3.297-1.274-6.81-1.744h-.008l-2.838-.38-.295.146c-1.09-.082-2.185-.226-3.27-.244zm-349.32.46c-4.49.056-9.02.665-13.538 1.876-.095.026-.327.068-.44.094l-.575-.574-5.76 2.377h-.002C27.32 36.99 13.11 77.635 23.69 117.12c4.574 17.073 13.46 32.977 24.845 44.67-9.328 6.978-16.34 15.908-21.053 25.99-6.507 13.924-8.973 29.83-9.11 46.6-.27 33.543 8.753 71.01 17.82 104.845l2.42 9.027 40.02-10.727 51.11 149.454 60.46.153-1.39-18.694-45.7-.116-52.446-153.37-38.73 10.378c-8.028-30.892-15.098-63.467-14.875-90.8.122-14.997 2.417-28.276 7.354-38.84 4.937-10.56 12.24-18.566 23.865-24.15l14.298-6.87-12.94-9.176c-11.456-8.122-23.12-25.39-27.896-43.215-8.66-32.315 3.867-62.596 24.653-71.188l.025-.01c.244-.1 1.86-.42 4.486-1.12h.002l.002-.003c2.966-.796 6.005-1.18 9.072-1.175 21.47.027 44.263 19.06 52.344 49.223 4.66 17.392 3.46 37.92-2.035 50.517l-6.436 14.76 16.01-1.734c13.355-1.447 23.684 1.234 32.868 7.016 4.285-4.866 9.108-9.17 14.46-12.742-.73-.536-1.464-1.062-2.212-1.572-9.55-6.512-20.777-10.598-33.283-11.522 3.562-15.46 3.09-33.105-1.318-49.56-9.878-36.864-39.338-63.538-70.77-63.14z")
	GenericFileSVG             = mustSVG(384, 512, "M224 136V0H24C10.7 0 0 10.7 0 24v464c0 13.3 10.7 24 24 24h336c13.3 0 24-10.7 24-24V160H248c-13.2 0-24-10.8-24-24zm160-14.1v6.1H256V0h6.1c6.4 0 12.5 2.5 17 7l97.9 98c4.5 4.5 7 10.6 7 16.9z")
	HierarchySVG               = mustSVG(576, 512, "M208 80c0-26.51 21.5-48 48-48h64c26.5 0 48 21.49 48 48v64c0 26.5-21.5 48-48 48h-8v40h152c30.9 0 56 25.1 56 56v32h8c26.5 0 48 21.5 48 48v64c0 26.5-21.5 48-48 48h-64c-26.5 0-48-21.5-48-48v-64c0-26.5 21.5-48 48-48h8v-32c0-4.4-3.6-8-8-8H312v40h8c26.5 0 48 21.5 48 48v64c0 26.5-21.5 48-48 48h-64c-26.5 0-48-21.5-48-48v-64c0-26.5 21.5-48 48-48h8v-40H112c-4.4 0-8 3.6-8 8v32h8c26.5 0 48 21.5 48 48v64c0 26.5-21.5 48-48 48H48c-26.51 0-48-21.5-48-48v-64c0-26.5 21.49-48 48-48h8v-32c0-30.9 25.07-56 56-56h152v-40h-8c-26.5 0-48-21.5-48-48V80z")
	ImageFileSVG               = mustSVG(384, 512, "M384 121.941V128H256V0h6.059a24 24 0 0 1 16.97 7.029l97.941 97.941a24.002 24.002 0 0 1 7.03 16.971zM248 160c-13.2 0-24-10.8-24-24V0H24C10.745 0 0 10.745 0 24v464c0 13.255 10.745 24 24 24h336c13.255 0 24-10.745 24-24V160H248zm-135.455 16c26.51 0 48 21.49 48 48s-21.49 48-48 48-48-21.49-48-48 21.491-48 48-48zm208 240h-256l.485-48.485L104.545 328c4.686-4.686 11.799-4.201 16.485.485L160.545 368 264.06 264.485c4.686-4.686 12.284-4.686 16.971 0L320.545 304v112z")
	LastSVG                    = mustSVG(512, 512, "M512 96.03v319.9c0 17.67-14.33 31.1-31.1 31.1-18.6.07-32.9-13.43-32.9-31.93v-131L276.5 440.6c-20.6 17.1-52.5 2.7-52.5-25.5v-131L52.5 440.6C31.88 457.7 0 443.3 0 415.1V96.03c0-27.37 31.88-41.74 52.5-24.62L224 226.8V96.03c0-27.37 31.88-41.74 52.5-24.62L448 226.8V96.03c0-17.67 14.33-31.1 31.1-31.1 18.6-.9 32.9 13.43 32.9 31.1z")
	MeleeWeaponSVG             = mustSVG(512, 512, "M240.094 19.594c-56.69.364-110.882 29.054-151.594 72.344-53.428 56.81-81.948 137.907-61.03 210.093 16.33-8.797 32.757-15.987 48.936-21.374-6.327-123.16 89.247-210.922 200.03-210.344 4.255-13.365 10.268-27.308 18.127-41.874-16.323-5.43-32.736-8.36-48.97-8.782-1.833-.047-3.67-.074-5.5-.062zM271.28 88.97c-97.556 1.745-179.913 77.1-176.373 186.31 10.986-2.73 21.788-4.582 32.28-5.436 14.59-1.187 28.69-.463 41.783 2.437L278.312 162.94a114.81 114.81 0 0 1-9.344-38.75c-.716-11.256.14-22.983 2.592-35.22-.093.002-.187 0-.28 0zm60.845 60.718-16.875 16.875L345.75 197l16.813-16.813-30.438-30.5zm-37.125 23L175.625 292.063l44.625 44.562 119.313-119.313L295 172.688zm189.875 46.093c-14.466 7.808-28.318 13.807-41.594 18.064.75 111.013-87.243 206.8-210.686 200.28-5.39 16.104-12.552 32.462-21.313 48.72 72.19 20.922 153.313-7.6 210.126-61.03 57.045-53.65 88.516-130.72 63.47-206.033zm-136 15.657L240.687 342.625c3.23 13.563 4.086 28.245 2.844 43.47-.862 10.58-2.752 21.476-5.53 32.56 109.585 3.718 185.128-79.008 186.594-176.905-12.342 2.506-24.16 3.403-35.5 2.688-14.287-.9-27.698-4.347-40.22-10zM169.5 312.313 20.094 461.72V494H48.75l151.188-151.188-30.438-30.5z")
	MenuSVG                    = mustSVG(448, 512, "M0 96c0-17.67 14.33-32 32-32h384c17.7 0 32 14.33 32 32 0 17.7-14.3 32-32 32H32c-17.67 0-32-14.3-32-32zm0 160c0-17.7 14.33-32 32-32h384c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.67 0-32-14.3-32-32zm416 192H32c-17.67 0-32-14.3-32-32s14.33-32 32-32h384c17.7 0 32 14.3 32 32s-14.3 32-32 32z")
	NextSVG                    = mustSVG(320, 512, "M287.1 447.1c17.67 0 31.1-14.33 31.1-32V96.03c0-17.67-14.33-32-32-32s-31.1 14.33-31.1 31.1v319.9c0 18.57 15.2 32.07 32 32.07zm-234.59-6.5 192-159.1c7.625-6.436 11.43-15.53 11.43-24.62 0-9.094-3.809-18.18-11.43-24.62l-192-159.1C31.88 54.28 0 68.66 0 96.03v319.9c0 27.37 31.88 41.77 52.51 24.67z")
	NotSVG                     = mustSVG(512, 512, "M512 256c0 141.4-114.6 256-256 256S0 397.4 0 256 114.6 0 256 0s256 114.6 256 256zM99.5 144.8C77.15 176.1 64 214.5 64 256c0 106 85.1 192 192 192 41.5 0 79.9-13.1 111.2-35.5L99.5 144.8zM448 256c0-106.9-86-192-192-192-41.5 0-79.9 13.15-111.2 35.5l267.7 267.7C434.9 335.9 448 297.5 448 256z")
	OpenFolderSVG              = mustSVG(576, 512, "M572.694 292.093L500.27 416.248A63.997 63.997 0 0 1 444.989 448H45.025c-18.523 0-30.064-20.093-20.731-36.093l72.424-124.155A64 64 0 0 1 152 256h399.964c18.523 0 30.064 20.093 20.73 36.093zM152 224h328v-48c0-26.51-21.49-48-48-48H272l-64-64H48C21.49 64 0 85.49 0 112v278.046l69.077-118.418C86.214 242.25 117.989 224 152 224z")
	PDFFileSVG                 = mustSVG(384, 512, "M181.9 256.1c-5-16-4.9-46.9-2-46.9 8.4 0 7.6 36.9 2 46.9zm-1.7 47.2c-7.7 20.2-17.3 43.3-28.4 62.7 18.3-7 39-17.2 62.9-21.9-12.7-9.6-24.9-23.4-34.5-40.8zM86.1 428.1c0 .8 13.2-5.4 34.9-40.2-6.7 6.3-29.1 24.5-34.9 40.2zM248 160h136v328c0 13.3-10.7 24-24 24H24c-13.3 0-24-10.7-24-24V24C0 10.7 10.7 0 24 0h200v136c0 13.2 10.8 24 24 24zm-8 171.8c-20-12.2-33.3-29-42.7-53.8 4.5-18.5 11.6-46.6 6.2-64.2-4.7-29.4-42.4-26.5-47.8-6.8-5 18.3-.4 44.1 8.1 77-11.6 27.6-28.7 64.6-40.8 85.8-.1 0-.1.1-.2.1-27.1 13.9-73.6 44.5-54.5 68 5.6 6.9 16 10 21.5 10 17.9 0 35.7-18 61.1-61.8 25.8-8.5 54.1-19.1 79-23.2 21.7 11.8 47.1 19.5 64 19.5 29.2 0 31.2-32 19.7-43.4-13.9-13.6-54.3-9.7-73.6-7.2zM377 105L279 7c-4.5-4.5-10.6-7-17-7h-6v128h128v-6.1c0-6.3-2.5-12.4-7-16.9zm-74.1 255.3c4.1-2.7-2.5-11.9-42.8-9 37.1 15.8 42.8 9 42.8 9z")
	PreviousSVG                = mustSVG(320, 512, "M31.1 64.03c-17.67 0-31.1 14.33-31.1 32v319.9c0 17.67 14.33 32 32 32 17.67-.83 32-14.33 32-32.83V96.03c0-17.67-14.33-32-32.9-32zm236.4 7.38-192 159.1C67.82 237.8 64 246.9 64 256c0 9.094 3.82 18.18 11.44 24.62l192 159.1c20.63 17.12 52.51 2.75 52.51-24.62V95.2c-.85-26.54-31.85-40.92-52.45-23.79z")
	RandomizeSVG               = mustSVG(512, 512, "M424.1 287c-15.13-15.12-40.1-4.426-40.1 16.97V352h-48L153.6 108.8c-6-8-15.5-12.8-25.6-12.8H32c-17.69 0-32 14.3-32 32s14.31 32 32 32h80l182.4 243.2c6 8.1 15.5 12.8 25.6 12.8h63.97v47.94c0 21.39 25.86 32.12 40.99 17l79.1-79.98c9.387-9.387 9.387-24.59 0-33.97L424.1 287zM336 160h47.97v48.03c0 21.39 25.87 32.09 40.1 16.97l79.1-79.98c9.387-9.391 9.385-24.59-.001-33.97l-79.1-79.98c-15.13-15.12-40.99-4.391-40.99 17V96H320c-10.06 0-19.56 4.75-25.59 12.81L254 162.7l39.1 53.3 42.9-56zM112 352H32c-17.69 0-32 14.31-32 32s14.31 32 32 32h96c10.06 0 19.56-4.75 25.59-12.81l40.4-53.87L154 296l-42 56z")
	RangedWeaponSVG            = mustSVG(512, 512, "m136.564 31.01 239.67 149.595c-12.418 21.234-20.756 28.302-45.027 46.936l156.3-26.33-85.603-125.474c4.936 24.85 8.85 38.5.75 60.49L136.568 31.01h-.004zM21.524 42.75l83.13 325.893c-21.017 5.232-30.98 3.262-58.875-3.96l124.046 113.45 13.426-166.844c-10.836 23.322-15.94 37.197-34.342 46.82L21.523 42.75zm64.353.215 252.2 353.16c-23.285 16.947-36.38 19.583-73.83 24.9l200.66 71.74L407.7 286.944c-2.477 33.743-2.313 53.14-20.37 74.09L85.877 42.965z")
	ResetSVG                   = mustSVG(512, 512, "M288 256c0 17.7-14.3 32-32 32s-32-14.3-32-32V32c0-17.67 14.3-32 32-32s32 14.33 32 32v224zm-208 0c0 97.2 78.8 176 176 176s176-78.8 176-176c0-54.4-24.7-103.1-63.5-135.4-13.6-11.3-15.5-31.47-4.2-45.06 11.3-13.59 31.5-15.44 45.1-4.14 52.8 44 86.6 110.4 86.6 183.7C496 388.5 388.5 496 256 496S16 388.5 16 255.1c0-73.3 33.75-139.7 86.6-183.7 13.6-11.3 33.8-9.45 45.1 4.14 10.4 13.59 9.4 33.76-4.2 45.06C104.7 152.9 80 201.6 80 256z")
	SearchSVG                  = mustSVG(512, 512, "M500.3 443.7 380.6 324c27.22-40.41 40.65-90.9 33.46-144.7C401.8 87.79 326.8 13.32 235.2 1.723 99.01-15.51-15.51 99.01 1.724 235.2c11.6 91.64 86.08 166.7 177.6 178.9 53.8 7.189 104.3-6.236 144.7-33.46l119.7 119.7c15.62 15.62 40.95 15.62 56.57 0 15.606-15.64 15.606-41.04.006-56.64zM79.1 208c0-70.58 57.42-128 128-128s128 57.42 128 128-57.42 128-128 128-128-57.4-128-128z")
	SettingsSVG                = mustSVG(512, 512, "M0 416c0-17.7 14.33-32 32-32h54.66C99 355.7 127.2 336 160 336c32.8 0 60.1 19.7 73.3 48H480c17.7 0 32 14.3 32 32s-14.3 32-32 32H233.3c-13.2 28.3-40.5 48-73.3 48s-61-19.7-73.34-48H32c-17.67 0-32-14.3-32-32zm192 0c0-17.7-14.3-32-32-32s-32 14.3-32 32 14.3 32 32 32 32-14.3 32-32zm160-240c32.8 0 60.1 19.7 73.3 48H480c17.7 0 32 14.3 32 32s-14.3 32-32 32h-54.7c-13.2 28.3-40.5 48-73.3 48s-61-19.7-73.3-48H32c-17.67 0-32-14.3-32-32s14.33-32 32-32h246.7c12.3-28.3 40.5-48 73.3-48zm32 80c0-17.7-14.3-32-32-32s-32 14.3-32 32 14.3 32 32 32 32-14.3 32-32zm96-192c17.7 0 32 14.33 32 32 0 17.7-14.3 32-32 32H265.3c-13.2 28.3-40.5 48-73.3 48s-61-19.7-73.3-48H32c-17.67 0-32-14.3-32-32 0-17.67 14.33-32 32-32h86.7C131 35.75 159.2 16 192 16s60.1 19.75 73.3 48H480zM160 96c0 17.7 14.3 32 32 32s32-14.3 32-32c0-17.67-14.3-32-32-32s-32 14.33-32 32z")
	SizeToFitSVG               = mustSVG(512, 512, "m503.1 273.6-112 104c-6.984 6.484-17.17 8.219-25.92 4.406s-14.41-12.45-14.41-22v-56l-192 .001V360a23.99 23.99 0 0 1-14.41 22c-8.75 3.812-18.94 2.078-25.92-4.406l-112-104c-9.781-9.094-9.781-26.09 0-35.19l112-104a24.014 24.014 0 0 1 25.92-4.406C154 133.8 159.7 142.5 159.7 152v55.1l192-.001v-56c0-9.547 5.656-18.19 14.41-22s18.94-2.078 25.92 4.406l112 104c9.77 9.995 9.77 26.995-.93 36.095z")
	StackSVG                   = mustSVG(512, 512, "M232.5 5.171a56.026 56.026 0 0 1 47 0L498.1 106.2c8.5 3.9 13.9 12.4 13.9 20.9 0 10.2-5.4 18.7-13.9 22.7l-218.6 101c-14.9 6.9-32.1 6.9-47 0l-218.57-101C5.438 145.8 0 137.3 0 127.1c0-8.5 5.437-17 13.93-20.9L232.5 5.171zM498.1 234.2c8.5 3.9 13.9 12.4 13.9 20.9 0 10.2-5.4 18.7-13.9 22.7l-218.6 101c-14.9 6.9-32.1 6.9-47 0l-218.57-101C5.438 273.8 0 265.3 0 255.1c0-8.5 5.437-17 13.93-20.9l53.2-24.6 151.97 70.2c23.4 10.9 50.4 10.9 73.8 0l152-70.2 53.2 24.6zM292.9 407.8l152-70.2 53.2 24.6c8.5 3.9 13.9 12.4 13.9 20.9 0 10.2-5.4 18.7-13.9 22.7l-218.6 101c-14.9 6.9-32.1 6.9-47 0l-218.57-101C5.438 401.8 0 393.3 0 383.1c0-8.5 5.437-17 13.93-20.9l53.2-24.6 151.97 70.2c23.4 10.9 50.4 10.9 73.8 0z")
	TrashSVG                   = mustSVG(448, 512, "M135.2 17.69C140.6 6.848 151.7 0 163.8 0h120.4c12.1 0 23.2 6.848 28.6 17.69L320 32h96c17.7 0 32 14.33 32 32s-14.3 32-32 32H32C14.33 96 0 81.67 0 64s14.33-32 32-32h96l7.2-14.31zM394.8 466.1c-1.6 26.2-22.5 45.9-47.9 45.9H101.1c-25.35 0-46.33-19.7-47.91-45.9L31.1 128H416l-21.2 338.1z")
	WeightSVG                  = mustSVG(512, 512, "m510.3 445.9-73-292.1c-3.8-15.3-16.5-25.8-30.9-25.8h-60.3c3.625-9.1 5.875-20.75 5.875-32 0-53-42.1-96-96-96S159.1 43 159.1 96c0 11.25 2.25 22 5.875 32H105.6c-14.38 0-27.13 10.5-30.88 25.75L1.71 445.85C-6.641 479.1 16.36 512 47.99 512h416c31.61 0 54.61-32.9 46.31-66.1zM256 128c-17.6 0-32.9-14.4-32.9-32s15.3-32 32.9-32c17.63 0 32 14.38 32 32s-14.4 32-32 32z")
)

func mustSVG(width, height float32, svgStr string) *unison.SVG {
	s, err := unison.NewSVG(unison.NewSize(width, height), svgStr)
	jot.FatalIfErr(err)
	return s
}
