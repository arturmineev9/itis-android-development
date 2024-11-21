package com.example.hw2_recyclerview.repository

import com.example.hw2_recyclerview.model.ButtonsHolderData
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.model.ViewHolderData

object TanksRepository {

    val items = listOf(
        ButtonsHolderData(
            id = "button1_id",
            headerText = "Button"
        ),
        ViewHolderData(
            id = "1",
            headerText = "T-90M \"Прорыв\"",
            description = "Модернизированная версия T-90. Имеет улучшенную броню, активную защиту \"Арена-М\" и новые системы управления огнём.",
            imageUrl = "https://cdnstatic.rg.ru/uploads/images/2022/06/26/rian_t-90_7e7.jpg",
        ),
        ViewHolderData(
            id = "2",
            headerText = "T-14 \"Армата\"",
            description = "Перспективный танк с необитаемой башней, радиолокационным комплексом и системой активной защиты \"Афганит\".",
            imageUrl = "https://sun9-77.userapi.com/impg/r5KgPITbl6vfWvk1Z20DtWSyqHHT1254vAUoKA/w9YBYi3N7Sk.jpg?size=1200x675&quality=96&sign=00857b70043f9621518f1163aba8b6d4&type=album",
        ),
        ViewHolderData(
            id = "3",
            headerText = "M1A2 Abrams",
            description = "Основной боевой танк США. Оснащён газотурбинным двигателем, композитной бронёй и современными системами управления огнём. Применялся в ряде конфликтов, включая Ирак.",
            imageUrl = "https://static.wikia.nocookie.net/nowheremash/images/c/cc/M1A2_Abrams.jpg/revision/latest/scale-to-width-down/1200?cb=20150716163755",
        ),
        ViewHolderData(
            id = "4",
            headerText = "Leopard 2A7",
            description = "Одна из самых современных модификаций Leopard 2. Отличается усиленной защитой, продвинутыми тепловизорами и мощной пушкой Rheinmetall L/55.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=70d00424ba520c5e6660ce708ba71a3f864a4bd7-4915723-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "5",
            headerText = "Challenger 2",
            description = "Британский танк с уникальной бронёй \"Чобхэм\" и высокой точностью 120-мм орудия с нарезным стволом.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=0dafe28632510d1cc31a9c8e955297c5e22fee66-9263927-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "6",
            headerText = "Merkava Mk. 4",
            description = "Танк с акцентом на защиту экипажа. Имеет передний двигатель, модульную броню и систему активной защиты \"Трофи\".",
            imageUrl = "https://avatars.dzeninfra.ru/get-zen_doc/3504171/pub_5f22b4689669f47c3eef0d34_5f22b5e26c45ec3edcffb3b2/scale_1200",
        ),
        ViewHolderData(
            id = "7",
            headerText = "K2 Black Panther",
            description = "Высокотехнологичный танк с системой активной подвески, мощным орудием и композитной бронёй.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=263f8ea054aba9dac6ac411a20d6242000887ffc-8259514-images-thumbs&n=13",
        ),

        ViewHolderData(
            id = "8",
            headerText = "Type 10",
            description = "Лёгкий и маневренный танк с мощным орудием, автоматом заряжания и электронной системой управления.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=e5e227c0f48859d3ecb73176109a4995-5877211-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "9",
            headerText = "Type 99A",
            description = "Основной боевой танк Китая, оснащённый системой активной защиты, мощным 125-мм орудием и современной электроникой.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=079f85e08ac4e55ee06cb10953deedda11c1f72c-5163093-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "10",
            headerText = "Altay",
            description = "Турецкий танк нового поколения с немецким двигателем и многослойной бронёй.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=a82e54469e8c36cd6d1268a5e03a05db3106e5c0bb679801-12511685-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "11",
            headerText = "T-72B3M",
            description = "Улучшенная версия T-72 с новой электроникой, усиленной бронёй и современным прицелом \"Сосна-У\".",
            imageUrl = "https://avatars.mds.yandex.net/i?id=e1ca4f6e1dc994b152ee696ca8f9484bd13badf6-3560695-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "12",
            headerText = "AMX-56 Leclerc",
            description = "Лёгкий и мобильный французский танк с автоматом заряжания, композитной бронёй и продвинутыми системами управления огнём.",
            imageUrl = "https://avatars.dzeninfra.ru/get-zen_doc/1936915/pub_5eba95e3b571c1699f124732_5ebba78a46a76d491981c03e/scale_1200",
        ),
        ViewHolderData(
            id = "13",
            headerText = "Strv 122",
            description = "Шведская модификация Leopard 2 с усиленной бронёй и системой активной защиты.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=95d89c832f6406ecaea273b82ee3b5a2b62b2c3c-11254239-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "14",
            headerText = "ZTZ-96B",
            description = "Усовершенствованная версия ZTZ-96, способная эффективно действовать в сложных условиях, включая высокогорье.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=a8d00e54d8817b7a64dd56eeba2d313c31c46fdebb1e47d0-5669789-images-thumbs&n=13",
        ),

        ViewHolderData(
            id = "15",
            headerText = "T-80БВМ",
            description = "Модернизированный T-80 с улучшенным вооружением, активной защитой и газотурбинным двигателем для суровых климатических условий.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=8caa0f61e69d6c3cc21c6c2b70e58b86bebd014ddb9cff16-10514338-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "16",
            headerText = "Т-34",
            description = "Легендарный танк Второй мировой войны, известный своей маневренностью и простотой.",
            imageUrl = "https://a.d-cd.net/fc32ce1s-960.jpg",
        ),
        ViewHolderData(
            id = "17",
            headerText = "PzKpfw IV",
            description = "Основной немецкий танк в годы Второй мировой, использовался на всех фронтах.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=64cdeaecfb1d426dc3ede4671d40d39ae56721cd-9065836-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "18",
            headerText = "Sherman M4",
            description = "Массовый американский танк Второй мировой, эффективный и надежный.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=20fc2d9f1476afcd707803c830df88a6590b6e63-8484564-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "19",
            headerText = "Tiger I",
            description = "Мощный тяжелый танк с грозным вооружением, но сложный в производстве.",
            imageUrl = "https://a.d-cd.net/KgAAAgC-lOA-1920.jpg",
        ),
        ViewHolderData(
            id = "20",
            headerText = "M1 Abrams",
            description = "Американский основной боевой танк с газотурбинным двигателем.",
            imageUrl = "https://overclockers.ru/st/legacy/blog/397468/400416_O.jpg",
        ),
        ViewHolderData(
            id = "21",
            headerText = "T-90",
            description = "Ссовременный российский танк с усовершенствованной броней и электроникой.",
            imageUrl = "https://steamuserimages-a.akamaihd.net/ugc/2026105606558874759/9CA339DA4B7A45850B3E04CD3BC3E72333829980/?imw=512&amp;&amp;ima=fit&amp;impolicy=Letterbox&amp;imcolor=%23000000&amp;letterbox=false",
        ),

        ViewHolderData(
            id = "22",
            headerText = "Type 59",
            description = "Китайский танк, основанный на конструкции Т-54.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=8ef1af4b248ba4bc42f33d739fce2eda40f3fd0c-10411335-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "23",
            headerText = "T-80",
            description = "Ппервый советский танк с газотурбинным двигателем.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=0de364619c201998db28f729fe1da1af3d6cde28-5674063-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "24",
            headerText = "ZTZ-96",
            description = "Китайский танк с улучшенными характеристиками на базе Type 85.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=a8d00e54d8817b7a64dd56eeba2d313c31c46fdebb1e47d0-5669789-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "25",
            headerText = "AMX-13",
            description = "Легкий французский танк с качающейся башней.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/78/AMX-13-latrun-2.jpg",
        ),
        ViewHolderData(
            id = "26",
            headerText = "T-55",
            description = "Один из самых массовых танков в истории, использовался в десятках конфликтов.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=66c16f4bdcf7bebee4d7e1e5e9ea4e1536f91f49238444b4-12421240-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "27",
            headerText = "Centurion",
            description = "Универсальный основной боевой танк, служивший на протяжении десятилетий.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=01d530c33841b7c7c7c1eed5168c385e650531dd-5102865-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "28",
            headerText = "Chieftain",
            description = "Тяжелый британский танк с мощным орудием и хорошей броней.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=0de12497100941fd532075b2a0914846081154be-3948822-images-thumbs&n=13",
        ), ViewHolderData(
            id = "29",
            headerText = "T-72",
            description = "Один из самых популярных и экспортируемых советских танков.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=080a05c83767ee0ee7b7c41ad59ef046a65ed8da-4869642-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "30",
            headerText = "Comet",
            description = "Модернизированный крейсерский танк, использовавшийся в конце Второй мировой.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=32d20fa5ac86415b966648170f674f7f4967b2f0-9867831-images-thumbs&n=13",
        ), ViewHolderData(
            id = "31",
            headerText = "ИС-2",
            description = "Тяжелый советский танк, созданный для борьбы с \"Тиграми\".",
            imageUrl = "https://avatars.mds.yandex.net/i?id=1e09c52dc8e634c4eea4295acc5ff65d3474b054-4548378-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "32",
            headerText = "Type 99",
            description = "Современный китайский основной боевой танк с мощным вооружением.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=83b0cf4bc93cb09ab6b255ca4078f6ae5961117c4555dd31-10208766-images-thumbs&n=13",
        ), ViewHolderData(
            id = "33",
            headerText = "Ariete",
            description = "Основной боевой танк Италии, разработанный в конце XX века.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=aa4831ccc085ff7ac239be68a5a3ca162d4f96f6-12624586-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "34",
            headerText = "М3 Stuart",
            description = "Легкий танк Второй мировой, широко использовавшийся в бою.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=04abe466c9c60133831dc655ffdf8e5cb60bb379-4579694-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "35",
            headerText = "M60 Patton",
            description = "Американский основной боевой танк холодной войны.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=b2564cd7af4748f0415f2322821ec00545ca4d8f-10234572-images-thumbs&n=13",
        ),
        ViewHolderData(
            id = "35",
            headerText = "K9 Thunder",
            description = "современная южнокорейская 155-мм самоходно-артиллерийская установка (САУ) класса самоходных гаубиц, была разработана в середине 1990-х годов корпорацией Samsung Techwin",
            imageUrl = "https://avatars.mds.yandex.net/i?id=6af29eb6a0b3ca329162617ef4ff79f56b5aaaab-4358711-images-thumbs&n=13",
        )
    )


    fun getItemById(id: String): MultipleHoldersData? {
        return items.find { it.id == id }
    }
}