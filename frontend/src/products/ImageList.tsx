import * as React from 'react';
import MuiImageList from '@material-ui/core/ImageList';
import ImageListItem  from '@material-ui/core/ImageListItem';
import ImageListItemBar from '@material-ui/core/ImageListItemBar';
import { makeStyles } from '@material-ui/core/styles';
import withWidth, { WithWidth } from '@material-ui/core/withWidth';
import {
    linkToRecord,
    NumberField,
    useListContext,
    DatagridProps,
    Identifier,
} from 'react-admin';
import { Link } from 'react-router-dom';
import { Breakpoint } from '@material-ui/core/styles/createBreakpoints';

const useStyles = makeStyles(theme => ({
    ImageList: {
        margin: 0,
    },
    tileBar: {
        background:
            'linear-gradient(to top, rgba(0,0,0,0.8) 0%,rgba(0,0,0,0.4) 70%,rgba(0,0,0,0) 100%)',
    },
    placeholder: {
        backgroundColor: theme.palette.grey[300],
        height: '100%',
    },
    price: {
        display: 'inline',
        fontSize: '1em',
    },
    link: {
        color: '#fff',
    },
}));

const getColsForWidth = (width: Breakpoint) => {
    if (width === 'xs') return 2;
    if (width === 'sm') return 3;
    if (width === 'md') return 3;
    if (width === 'lg') return 5;
    return 6;
};

const times = (nbChildren: number, fn: (key: number) => any) =>
    Array.from({ length: nbChildren }, (_, key) => fn(key));

const LoadingImageList = (props: GridProps & { nbItems?: number }) => {
    const { width, nbItems = 20 } = props;
    const classes = useStyles();
    return (
        <MuiImageList
            rowHeight={180}
            cols={getColsForWidth(width)}
            className={classes.ImageList}
        >
            {' '}
            {times(nbItems, key => (
                <ImageListItem key={key}>
                    <div className={classes.placeholder} />
                </ImageListItem>
            ))}
        </MuiImageList>
    );
};

const LoadedImageList = (props: GridProps) => {
    const { width } = props;
    const { ids, data, basePath } = useListContext();
    const classes = useStyles();

    if (!ids || !data) return null;

    return (
        <MuiImageList
            rowHeight={180}
            cols={getColsForWidth(width)}
            className={classes.ImageList}
        >
            {ids.map((id: Identifier) => (
                <ImageListItem
                    // @ts-ignore
                    component={Link}
                    key={id}
                    to={linkToRecord(basePath, data[id].id)}
                >
                    <img src={data[id].thumbnail} alt="" />
                    <ImageListItemBar
                        className={classes.tileBar}
                        title={data[id].reference}
                        subtitle={
                            <span>
                                {data[id].width}x{data[id].height},{' '}
                                <NumberField
                                    className={classes.price}
                                    source="price"
                                    record={data[id]}
                                    color="inherit"
                                    options={{
                                        style: 'currency',
                                        currency: 'USD',
                                    }}
                                />
                            </span>
                        }
                    />
                </ImageListItem>
            ))}
        </MuiImageList>
    );
};

interface GridProps extends Omit<DatagridProps, 'width'>, WithWidth {}

const ImageList = (props: WithWidth) => {
    const { width } = props;
    const { loaded } = useListContext();
    return loaded ? (
        <LoadedImageList width={width} />
    ) : (
        <LoadingImageList width={width} />
    );
};

export default withWidth()(ImageList);
